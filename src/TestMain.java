import java.io.IOException;
import java.io.PrintWriter;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NCdumpW;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;


public class TestMain {


	// Test class
	// You must download bathymetry data sepearely, e.g. from:
	// https://www.gebco.net/data_and_products/gridded_bathymetry_data/
	public static void main(String[] args) throws IOException, InvalidRangeException {
		
		String fileName = "GEBCO_2019.nc";
		
		DepthReader.readNetcdfFile(fileName);
		
		System.out.println("Alps: "+DepthReader.get_depth(45.845419, 7.686319));
		System.out.println("Himalayas: "+DepthReader.get_depth( 28.600454, 83.940480));
		System.out.println("Everest: "+DepthReader.get_depth( 27.9881, 86.9250));
		System.out.println("Mariana Trench: "+DepthReader.get_depth( 17.759441, 142.525087));
		
		DepthReader.closeFile();
	}
	


}
