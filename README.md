<div align="center">
  <h1>🔢 Calcitron Number System Converter</h1>
  <h3>Seamlessly Convert Between Decimal, Binary, Octal, and Hexadecimal</h3>
  
  [![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://play.google.com/store/apps/details?id=com.calcitron.converter)
  [![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
  [![Version](https://img.shields.io/badge/Version-1.0.0-important.svg)]()
  
  <img src="app/src/main/res/mipmap-xxhdpi/calcitron_logo.webp" alt="Calcitron Logo" width="200"/>
</div>

## 🌟 Overview
Calcitron is a powerful yet simple number system converter that helps students, developers, and professionals convert between decimal, binary, octal, and hexadecimal number systems with detailed, step-by-step explanations. Perfect for learning and verifying your number system conversions!

## 🚀 Key Features

### 🔢 Number System Conversion
- **Decimal ↔ Binary**: Convert between base-10 and base-2 systems
- **Decimal ↔ Octal**: Convert between base-10 and base-8 systems
- **Decimal ↔ Hexadecimal**: Convert between base-10 and base-16 systems
- **Binary ↔ Octal/Hexadecimal**: Direct conversion between different bases
- **Step-by-Step Solutions**: Detailed explanations for every conversion

### 📝 Conversion Methods
- **Integer Conversions**: 
  - Division-Remainder Method
  - Power of 2/8/16 Method
  - Direct Substitution
- **Fractional Conversions**:
  - Multiplication Method
  - Successive Division Method
- **Two's Complement**: For negative number representation

### ⚡ Features
- **Bitwise Operations**: AND, OR, XOR, NOT, Shift operations
- **Number Formatting**: Group digits for better readability
- **Copy to Clipboard**: One-tap copy of results
- **Dark/Light Theme**: Choose your preferred interface style

## 📱 Android Installation

### Manual APK Installation
1. [Download the latest APK](https://github.com/Anant-4-code/Calcitron-Digital-ELE-Converter/releases/latest)
2. On your Android device, go to Settings > Security
3. Enable "Unknown sources" (if not already enabled)
4. Open the downloaded APK file
5. Tap "Install" and follow the on-screen instructions

## 🔢 Conversion Examples

### 1. Decimal to Binary Conversion
**Example:** Convert 29 to binary

**Step 1:** Divide by 2 and record remainders
```
29 ÷ 2 = 14 remainder 1
14 ÷ 2 = 7  remainder 0
7  ÷ 2 = 3  remainder 1
3  ÷ 2 = 1  remainder 1
1  ÷ 2 = 0  remainder 1
```
**Step 2:** Write remainders in reverse order
**Result:** 29₁₀ = 11101₂

### 2. Binary to Hexadecimal
**Example:** Convert 11010110 to hexadecimal

**Step 1:** Group into 4 bits (add leading zeros if needed)
`1101 0110`

**Step 2:** Convert each group
`1101` = D
`0110` = 6

**Result:** 11010110₂ = D6₁₆

### 3. Decimal to Octal
**Example:** Convert 143 to octal

**Step 1:** Divide by 8 and record remainders
```
143 ÷ 8 = 17 remainder 7
 17 ÷ 8 = 2  remainder 1
  2 ÷ 8 = 0  remainder 2
```
**Step 2:** Write remainders in reverse order
**Result:** 143₁₀ = 217₈


## 🏗️ Project Structure
```
CalcitronAi/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/calcitron/
│   │   │   │   ├── activities/    # App screens
│   │   │   │   ├── adapters/      # RecyclerView adapters
│   │   │   │   ├── models/        # Data models
│   │   │   │   ├── utils/         # Utility classes
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/               # Resources
│   │   │   │   ├── drawable/      # Images and icons
│   │   │   │   ├── layout/        # XML layouts
│   │   │   │   └── values/        # Strings, colors, styles
│   │   │   └── AndroidManifest.xml
│   │   └── test/                 # Unit tests
│   └── build.gradle              # App level build config
├── build.gradle                  # Project level build config
└── settings.gradle              # Project settings
```

## 🛠️ System Requirements
- **Android 8.0 (API 26) or higher**
- **Minimum 2GB RAM** (4GB recommended)
- **100MB free storage** (for app installation)
- **Internet connection** (for cloud features and updates)

## 👥 Join Our Community

### 🤝 Contributing
✨ We're thrilled you want to contribute! Here's how you can help make Calcitron even better:

1. 🍴 **Fork** the repository
2. 🌿 Create a feature branch: `git checkout -b feature/amazing-feature`
3. 💾 Commit your changes: `git commit -m '✨ Add amazing feature'`
4. 🚀 Push to the branch: `git push origin feature/amazing-feature`
5. 🔄 Open a **Pull Request**

---

<div align="center">
  Made with Android Studio | Happy Converting! 🚀
</div>
