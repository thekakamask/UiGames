# UiGames
UiGames is a simple Android application developed with Jetpack Compose, offering multiple interactive activities: a dice roller, a lemonade-making game, a tip calculator, and an art gallery viewer.

## Features

### WelcomeActivity
- A home screen with buttons (with icons) that allows users to navigate to the various activities:
  - Dice Roller
  - Lemonade Creation
  - Tip Calculator
  - Art Gallery
- Each button is equipped with relevant icons and clean material design.

### RollActivity
- Roll a virtual die with a button to generate a random number (1 to 6).
- Displays the graphic representation of the die face.
- Navigate back to the WelcomeActivity.

### LemonadeActivity
- An interactive game where the user goes through several stages: picking a lemon, squeezing the lemon, drinking the lemonade, and starting over.
- Stages are based on user interactions, dynamic image changes, and text feedback.
- Navigate back to the WelcomeActivity.

### TipTimeActivity
- A tool to calculate tips based on the bill amount and the percentage entered.
- Provides the option to round up the tip.
- Clean interface with input fields and a navigation button back to WelcomeActivity.

### ArtGalleryActivity
- View a series of paintings in a swipeable gallery.
- Swipe left or right to navigate through the gallery, with each swipe showing one painting.
- The paintings have titles, artist names, and descriptions, and a tooltip can be toggled to show additional information about the painting.
- Buttons allow navigation back to the WelcomeActivity and to the next or previous painting.

## Technology Stack

- **Jetpack Compose**: For building responsive and modern user interfaces declaratively.
- **Material Design 3**: For styling and consistency across UI elements.
- **Multi-screen orientation support**: Custom layouts for both portrait and landscape modes.
- **Navigation between activities**: Seamless transitions between various activities with buttons.
- **Vector icons**: Custom vector icons used for buttons, ensuring scalability across different screen sizes.

## Objective

This app demonstrates how to effectively use Jetpack Compose to build modern, user-friendly interfaces while incorporating interactive activities, such as rolling dice, making lemonade, calculating tips, and viewing artwork.