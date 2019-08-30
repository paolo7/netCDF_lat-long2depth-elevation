import java.io.IOException;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * This class provides a method to compute ground elevation/sea depth given lat long coordinates
 * It is designed to work with bathymetry data in netCDF format, 
 * and in was tested with this dataset:
 * https://www.gebco.net/data_and_products/gridded_bathymetry_data/
 * 
 * The required netCDF format should contain three variables:
 * lat, long: two arrays of lat/long coordinates (not necessarily ordered)
 * elevation: a matrix with dimensions as lat x long, with elevation/depth measure for each pair of coordinates
 * 
 * @author paolo
 *
 */

public class DepthReader {

	/**
	 * This needs to be initialised before 
	 */
	private static NetcdfFile ncfile;
	
	/**
	 * Get depth for lat and long coordinates
	 * @param lat
	 * @param lon
	 * @return
	 * @throws IOException
	 * @throws InvalidRangeException
	 */
	public static double get_depth(double lat, double lon) throws IOException, InvalidRangeException {
		if(ncfile == null) throw new RuntimeException("ERROR: Before reading depth, initialise the NetcdfFile object with function readNetcdfFile");
		Variable latArray = ncfile.findVariable("lat");
		Variable lonArray = ncfile.findVariable("lon");
		Variable elevationArray = ncfile.findVariable("elevation");
		int latIndex = get_closest_index(lat, latArray);
		int lonIndex = get_closest_index(lon, lonArray);
		Array data = elevationArray.read(latIndex+", "+lonIndex);
		
		return data.getDouble(0);
	}
	
	public static void readNetcdfFile(String file) throws IOException {
		ncfile = NetcdfFile.open(file);
	}

	/**
	 * Finds the index in the array that better approximates the lat or long value provided
	 * @param value
	 * @param var
	 * @return
	 * @throws IOException
	 * @throws InvalidRangeException
	 */
	public static int get_closest_index(double value, Variable var) throws IOException, InvalidRangeException {
		double min_distance = Double.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < var.getDimension(0).getLength(); i++) {
			double current_value = var.read(i+"").getDouble(0);
			double difference = Math.abs(current_value - value);
			if(difference < min_distance) {
				min_distance = difference;
				index = i;
			}
		}
		return index;
	}
	
	public static void closeFile() throws IOException {
		ncfile.close();
	}
}
