#!/bin/bash

# Detect OS
OS="$(uname)"
echo "Detected OS: $OS"

# Check if Conda is already installed
if command -v conda >/dev/null 2>&1; then
  echo "Conda is already installed."
else
  echo "Conda is not installed. Proceeding with installation..."
  
  # Miniconda installation
  if [[ "$OS" == "Linux" ]]; then
    echo "Setting up Miniconda for Linux..."
    mkdir -p ~/miniconda3
    wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh -O ~/miniconda3/miniconda.sh
    bash ~/miniconda3/miniconda.sh -b -u -p ~/miniconda3
    source ~/miniconda3/bin/activate
  elif [[ "$OS" == "Darwin" ]]; then
    echo "Setting up Miniconda for macOS..."
    mkdir -p ~/miniconda3
    curl https://repo.anaconda.com/miniconda/Miniconda3-latest-MacOSX-arm64.sh -o ~/miniconda3/miniconda.sh
    bash ~/miniconda3/miniconda.sh -b -u -p ~/miniconda3
    source ~/miniconda3/bin/activate
  else
    echo "Unsupported OS: $OS"
    exit 1
  fi
fi


# Initialize Conda for the script
eval "$(conda shell.bash hook)"
# Conda setup
echo "Creating Conda environment..."
conda env create -f environment.yml

echo "Activating Conda environment"
conda activate uranus_env

echo "Updating Conda base environment..."
#conda update -n base -c defaults conda

# Check Java installation
echo "Checking Java installation..."
if command -v java >/dev/null 2>&1 && command -v javac >/dev/null 2>&1; then
  echo "Java and Javac are installed."
else
  echo "Java or Javac not found. Please install them. you can use java -version and javac -version"
  exit 1
fi

# Check Maven installation
echo "Checking Maven installation..."
if command -v mvn >/dev/null 2>&1; then
  echo "Maven is installed."
else
  echo "Maven not found. Please install it."
  exit 1
fi

# Application setup
echo "Setting up the application..."


# Navigate to the application directory
cd uranus/ || { echo "Directory 'uranus/' not found."; exit 1; }

# Run Django migrations
echo "Running Django migrations..."
python manage.py migrate

# Create Django superuser
echo "Creating Django superuser..."
python manage.py createsuperuser



# Navigate to the Java build directory
echo "Building Java components..."
cd ../java/UranusCB-V2.0/ || { echo "Directory '../java/UranusCB-V2.0/' not found."; exit 1; }

# Clean and package with Maven
mvn clean package

# Make the launch script executable
chmod +x launch.sh

# Return to the main directory and start the server
cd - || exit
echo "Starting the Django development server..."
python manage.py runserver

echo "Setup and application startup completed successfully."

