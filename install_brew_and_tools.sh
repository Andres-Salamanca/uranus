#!/bin/bash

# Check if Homebrew is already installed
if ! command -v brew &> /dev/null
then
    echo "Homebrew not found, installing..."
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
else
    echo "Homebrew is already installed."
fi

# Add Homebrew to the PATH in ~/.zshrc if it's not already there
if ! grep -q "/opt/homebrew/bin" ~/.zshrc; then
    echo 'export PATH="/opt/homebrew/bin:$PATH"' >> ~/.zshrc
    echo "Added Homebrew to the PATH in ~/.zshrc."
else
    echo "Homebrew is already in the PATH."
fi

source ~/.zshrc

# Install Maven and OpenJDK 11
echo "Installing Maven and OpenJDK 11..."
brew install maven openjdk@11

# Add JAVA_HOME and OpenJDK to the PATH in ~/.zshrc if it's not already there
if ! grep -q "JAVA_HOME" ~/.zshrc; then
    echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@11"' >> ~/.zshrc
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
    echo "Added OpenJDK 11 and JAVA_HOME to the PATH in ~/.zshrc."
else
    echo "JAVA_HOME and OpenJDK 11 are already configured."
fi

# Source the .zshrc file to apply the changes immediately
echo "Applying changes to the current session..."
source ~/.zshrc

# Confirmation
echo "Homebrew, Maven, and OpenJDK 11 have been installed and configured!"

