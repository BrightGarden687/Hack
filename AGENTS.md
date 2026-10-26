# AGENTS.md

Gradle multi-project Minecraft client mod, group `lot.hack`, mod id `hack`.

- `client/` — the Fabric mod (only Loom project, produces `client/build/libs/hack-1.0.0.jar`).
- `ai-slop/` — standalone plain `java-library` with zero dependencies (hand-rolled MLP/backprop). **Not wired into the client build** — client code cannot import `lot.hack.aislop.*`.

## Toolchain

- Java 25 is enforced at the root (`options.release = 25` in `build.gradle.kts`). Compile with a JDK that supports it; CI pins JDK 25.
- Use the wrapper (`./gradlew`), never a system gradle. Gradle 9.8.2. Configuration cache is enabled.
- All versions live in root `gradle.properties`: `minecraft_version` (26.3), `loader_version`, `loom_version` (a **-SNAPSHOT**, so resolution can silently move — pin a concrete version if a fresh resolution breaks the build), `fabric_api_version`. Bump there, never in the subproject build files. `fabric.mod.json` has a `${version}` placeholder expanded by `processResources` — leave it.
- Loom resolves **Mojang official mappings** by default here: class/method names are Mojang (`net.minecraft.client.Minecraft`, `LocalPlayer`, `Input.sprint()`), NOT Yarn (`MinecraftClient`, `ClientPlayerEntity`). Don't "translate" them.
- MixinExtras annotations (`@ModifyExpressionValue`) come bundled with Fabric Loader — no build dependency is declared and none is needed.

## Commands

- `./gradlew build` — the only automated verification; there are no test suites. CI (`.github/workflows/build.yml`) runs it on every push/PR.
- `./gradlew :client:runClient` — launches Minecraft with the mod dev-launched. Game dir is `client/run/` (saves, `options.txt`, logs, crash reports). Avoid globbing/reading `client/run/**` — it's binary game data, not project code.
- `./gradlew :ai-slop:build`.

## Conventions (client)

Everything is static singletons with private constructors — registration is explicit code, no DI or annotation scanning:

- Modules extend `ClientModule` with `public static final ModuleX INSTANCE`, placed in `lot.hack.module.modules.<category>/` matching their `ModuleCategory` (COMBAT/MISC/MOVEMENT/PLAYER/VISUAL/WORLD). The super constructor self-registers into `Modules.INSTANCE` and the category; also call `ModuleX.INSTANCE.initialize()` from `Modules.initialize()`.
- Settings are public fields created with the inherited builders: `booleanValue("Name", default)`, `intValue`, `floatValue(name, def, min, max, step)`, `intRangeValue`, `floatRangeValue`, `group()`, `tree()`, `.visible(() -> ...)`.
- Events are singletons extending `Event`/`CancellableEvent` (e.g. `EventTick.INSTANCE`). Mixins call `event.call()`; for cancellable events the flag resets on every `call()` and the mixin checks `isCancelled()` then `ci.cancel()`. Dispatch goes through `EventListenersCaller`, gated by `shouldListenEvents()` (for modules: in-game && toggled; `ModuleSprint` overrides it to `false`).
- WIP caveat: **nothing yet calls `EventListenersCaller.register(...)`** (nor `RotateCaller.register/rotate`) — modules won't actually receive events or drive rotation until that wiring exists. Don't assume `onEvent` or `rotate()` fires.
- `Rotation` API: `add`/`substact`/`multiply`/`minus` mutate in place; `added`/`substacted`/`multiplid`/`divided` return copies. The misspellings are the real names — match them.

## Mixins

- New mixin classes must be added to `client/src/main/resources/hack.mixins.json` manually (no auto-discovery).
- The config is `"required": true` with `defaultRequire 1` — a mismatching target method/point (e.g. after a Minecraft version bump) hard-crashes the game on startup, while `./gradlew build` still passes.
- Build success ≠ working client: the newest crash report shows `MixinFirstPersonHandsAndItemsRenderer.submitHandsWithItems` failing its injection check ("Scanned 0 target(s)"), so `runClient` crashes during init at time of writing. If `runClient` crashes, read `client/run/crash-reports/` (newest) for the failing mixin before changing code.
