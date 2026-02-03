# MiVideoPlay

MiVideoPlay 是一个基于最新的 Android 技术栈开发的简洁、高效的视频播放器应用。

## 📖 项目简介

本项目旨在演示如何结合 **Jetpack Compose** 和 **Media3 ExoPlayer** 构建现代化的 Android 视频播放体验。它支持从设备本地挑选视频文件，并提供完善的全屏切换和播放控制功能。

## ✨ 核心功能

- 📂 **本地视频选择**：通过 Android 系统选择器快速加载本地视频。
- 📺 **流畅播放体验**：基于 `Media3 ExoPlayer`，支持多种视频格式的高性能播放。
- 📱 **全屏旋转支持**：智能处理横竖屏切换，自动隐藏/显示系统状态栏和导航栏。
- 🛠️ **自定义控制界面**：完全控制播放、暂停、进度条以及全屏开关，提升交互体验。
- 🎨 **现代化 UI**：使用 `Jetpack Compose` 构建，界面响应迅速且易于扩展。

## 🛠️ 技术实现

### 1. UI 架构 (Jetpack Compose)
项目完全采用声明式 UI 框架 **Jetpack Compose**。
- `MainActivity` 作为入口，通过 `rememberLauncherForActivityResult` 异步获取视频 URI。
- `VideoPlayerScreen` 负责整体布局控制。
- `PlayerControls` 提供悬浮的交互控件。

### 2. 播放引擎 (Media3 ExoPlayer)
集成 Google 官方推荐的播放库：
- 使用 `ExoPlayer.Builder` 初始化播放器实例。
- 通过 `LaunchedEffect` 监听 URI 变化并动态更新 `MediaItem`。
- 使用 `DisposableEffect` 确保在组件销毁时释放播放器资源，避免内存泄漏。

### 3. 全屏与系统 UI 控制
- 使用 `WindowCompat` 和 `WindowInsetsControllerCompat` 实现沉浸式全屏。
- 动态调整 `Activity` 的 `requestedOrientation` 来切换横屏。

## 🚀 快速开始

1. **环境准备**：确保你的 Android Studio 已更新至最新版，且安装了支持 Compose 的 SDK。
2. **克隆仓库**：
   ```bash
   git clone https://github.com/lgsd123456/MiVideoPlay.git
   ```
3. **运行**：在 Android Studio 中打开项目，连接真机或模拟器运行即可。

## 📝 许可证

本项目遵循 MIT 许可证。
