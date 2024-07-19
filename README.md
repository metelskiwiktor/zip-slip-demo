# Stwórz plik zip

```python
import zipfile
import os

# Create a directory to store the files
os.makedirs("zip_slip_demo", exist_ok=True)

# Create a file with a relative path that attempts to escape the target directory
with open("zip_slip_demo/zip-file.txt", "w") as f:
    f.write("This is a zip-file text file.")

# Create the ZIP file
with zipfile.ZipFile("zip_slip-attack.zip", "w") as zipf:
    # Add the file with a path that will try to escape the extraction directory
    zipf.write("zip_slip_demo/zip-file.txt", "../sensitive/malicious-file.txt")

```

# Klasa `ZipSlipDemo`

Klasa wypakowuje zip plik z katalogu `resources` do katalogu `output`, jednak przy braku walidacji i wczytaniu
atakującego zipa `zip_slip-attack.zip`, zip zostanie wypakowany do folderu `sensitive` do którego nie powinien mieć
dostępu.

### Możesz zakomentować metodę `preventZipSlipAttack` by zapobiec lub umożliwić atak zip

```java
//zapobiegaj zip slip atak
preventZipSlipAttack(entry);
```

### Wybierz zip atakujący lub nieatakujący

```java
String zipFileName = ATTACK_ZIP;
String zipFileName = NORMAL_ZIP;
```