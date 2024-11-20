import os
import nltk

# Obtener el directorio actual
current_dir = os.path.dirname(os.path.abspath(__file__))

# Descargar los datos de NLTK en el directorio actual
nltk.download('stopwords', download_dir=current_dir)

# Asegurar que NLTK busque recursos en el directorio actual
nltk.data.path.append(current_dir)

