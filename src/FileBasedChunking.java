import java.io.File;
import java.util.Hashtable;


public class FileBasedChunking {
	public Hashtable<String, String> indexTable;
	public Checksum sum;
	public FileList list;
	public int count;
	
	public FileBasedChunking(){
		indexTable = new Hashtable<String, String>();
		sum = new Checksum();
		list = new FileList(Config.DIRECTORY);
		count = 0;
	}
	
	public void setAll(File[] fileList){
		for (File f : fileList) {
			if (f.isDirectory()) {
//				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				String checksum = sum.generateChecksum(f.getAbsolutePath(), f.getName());
				if(duplicateDetection(checksum)){
					System.out.println(++count + " duplicate files found: "+ f.getName());
				}else{
					indexTable.put(checksum, f.getName());
				}
			}
		}
	}
	
	public boolean duplicateDetection(String hashvalue){
		return indexTable.containsKey(hashvalue);
	}
	
	public static void main(String[] args){
		FileBasedChunking fbc = new FileBasedChunking();
		fbc.setAll(fbc.list.filelist);
	}
}