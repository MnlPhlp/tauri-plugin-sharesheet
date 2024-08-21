![sharesheet](https://github.com/tauri-apps/plugins-workspace/raw/v2/plugins/sharesheet/banner.png)

Share content to other apps via the Android Sharesheet or iOS Share Pane.

## Install

_This plugin requires a Rust version of at least **1.65**_

There are three general methods of installation that we can recommend.

1. Use crates.io and npm (easiest, and requires you to trust that our publishing pipeline worked)
2. Pull sources directly from Github using git tags / revision hashes (most secure)
3. Git submodule install this repo in your tauri project and then use file protocol to ingest the source (most secure, but inconvenient to use)

Install the Core plugin by adding the following to your `Cargo.toml` file:

`src-tauri/Cargo.toml`

```toml
[dependencies]
tauri-plugin-sharesheet = "2.0.0-beta"
# alternatively with Git:
tauri-plugin-sharesheet = { git = "https://github.com/tauri-apps/plugins-workspace", branch = "v2" }
```

You can install the JavaScript Guest bindings using your preferred JavaScript package manager:

> Note: Since most JavaScript package managers are unable to install packages from git monorepos we provide read-only mirrors of each plugin. This makes installation option 2 more ergonomic to use.

<!-- Add the branch for installations using git! -->

```sh
pnpm add @tauri-apps/plugin-sharesheet
# or
npm add @tauri-apps/plugin-sharesheet
# or
yarn add @tauri-apps/plugin-sharesheet

# alternatively with Git:
pnpm add https://github.com/tauri-apps/tauri-plugin-sharesheet#v2
# or
npm add https://github.com/tauri-apps/tauri-plugin-sharesheet#v2
# or
yarn add https://github.com/tauri-apps/tauri-plugin-sharesheet#v2
```

## Usage

First you need to register the core plugin with Tauri:

`src-tauri/src/main.rs`

```rust
fn main() {
    tauri::Builder::default()
        .plugin(tauri_plugin_sharesheet::init())
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

Afterwards all the plugin's APIs are available through the JavaScript guest bindings:

```javascript
import { shareText } from "@tauri-apps/plugin-sharesheet";
shareText('Tauri is great!');
```

## Contributing

PRs accepted. Please make sure to read the Contributing Guide before making a pull request.

## License

MIT