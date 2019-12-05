# netCDF_lat-long2depth-elevation

- Java class with Maven dependencies to converts lat long coordinates to depth or elevation.
- This requires netCDF bathymetry data, such as the one provided [GEBCO](https://www.gebco.net/data_and_products/gridded_bathymetry_data/])
- Before calling any function to compute depth, configure the `DepthReader` class to point to the dataset on your file system by calling the `readNetcdfFile` function.
