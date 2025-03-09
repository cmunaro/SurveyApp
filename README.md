# Survey Kata Application

## Cool Stuff Used

*   **Compose:** Everyone loves Compose.
*   **Clean Architecture:** Segregates application layers by feature and layer (see `screens/survey/<Layer>`). Since the main screen is logic-free, it doesn't follow this pattern. This approach is useful for maintaining large and complex applications.  
    In real-world applications, feature segregation often leads to scenarios where an object is needed by multiple features. In such cases, a simple solution is to move the shared class into a `shared/<Layer>` package eventually within a shared module.
*   **Multi-module:** Not really 😛, but it's worth mentioning that with the current architecture, you can choose to extract a module along a feature or a Clean Architecture layer if a feature (`screen`) or layer becomes large enough.
*   **Functional Reactive Programming (FRP) Principles:** This app has two versions of FRP:  
    - The `master` branch uses coroutines in a standard `suspend` style.  
    - The `flowEverywhereVersion` branch replaces `suspend` with `Flow` and its operators in the `ui` (only VM), `domain`, and `data` layers.
*   **Unit Tests:** This application includes both unit and UI tests using Kotest and MockK. The most logic-heavy parts are tested to showcase known approaches and methods.
*   **MVVM with Unidirectional Data Flow:** This application follows the MVVM architecture, where the ViewModel exposes a single immutable state and multiple actions. The UI observes and renders the state while sending events back to the ViewModel from the bottom of the composition graph to the top, the VM then handle the event and update its state (similar to MVI).  
    To enforce this pattern, each ViewModel ideally extends `StateViewModel` and uses `updateState` to modify its state. In real-world applications, a `Channel` could also be used to deliver one-time events to the UI.
*   **Mavericks-like Async State Handling:** To manage different states (Loading, Error, Success) without using `null`, this application follows an approach inspired by [Mavericks](https://github.com/airbnb/mavericks). A simplified version of `Async` is defined in `utils` to handle all possible states of a piece of data.
*   **Koin:** Used for fast, stable, and boilerplate-free Dependency Injection.

**Note:** Since the `master` branch is already fully reactive, in the `flowEverywhereVersion` branch, everything works without modifying the UI or `StateViewModel`.
