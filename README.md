# NeonSeekBar

A custom Android SeekBar with a neon-style UI.

## Features
- Customizable neon glow effect
- Smooth animations
- Adjustable colors and styles
- Changeable shadow color
- Configurable step size and value range

## Usage

Add `NeonSeekBar` to your XML layout:

```xml
<com.example.neonseekbar.NeonSeekBar
    android:id="@+id/neonSeekbar"
    android:layout_width="350dp"
    android:layout_height="wrap_content"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintBottom_toBottomOf="parent"
    tools:stepSize="1"
    tools:value="2"
    android:visibility="visible"
    tools:valueFrom="0"
    tools:valueTo="5" />
```

### Customize in Kotlin

```kotlin
val seekBar = findViewById<NeonSeekBar>(R.id.neonSeekBar)
seekBar.setGlowColor(Color.RED)
seekBar.setProgress(50)
seekBar.setShadowColor(Color.BLUE) // Change shadow color
```

## Screenshots
![Screenshot 1]
![demo](https://github.com/user-attachments/assets/35cd6621-7946-4999-884e-63353510bf90)



## Demo Video
[![Watch the video]
https://github.com/user-attachments/assets/9950cd52-1468-4271-9bda-021d6dbcd296



## How to Contribute
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit (`git commit -m "Your commit message"`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a Pull Request.


