package TinyTestJ;


import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Image {
	private int width=0;
	private int height=0;
	public int[] data;
	private int v=0;
	private int[]ConvertData=new int[3];
	
	public Image(int w,int h){
		this.width=w;
		this.height=h;
		int p=0;
		data=new int[3*(this.width*this.height)];
		for(int y=0;y<this.height;y++) {
			for(int x=0;x<this.width;x++) {
				for(int count=0;count<3;count++) {
					data[p]=0;
					p++;
				}
			}
		}
	}
	public void translate(int value){
		this.v=value;
		String y=Integer.toHexString(v);
		int a=v%(16*16*16*16);
		int b=a%(16*16);
		if((v/(16*16*16*16))>0){
			if(v>1048576) {
				this.ConvertData[0]=Integer.parseInt(y.substring(y.length()-6, y.length()-4),16);	
			}
			else {
				this.ConvertData[0]=Integer.parseInt(y.substring(y.length()-5, y.length()-4));	
			}	
		}
		else {
			this.ConvertData[0]=Integer.parseInt("00");
		}
		if(a/(16*16)>0){
			if(a>4096) {
				this.ConvertData[1]=Integer.parseInt(y.substring(y.length()-4, y.length()-2),16);	
			}
			else {
				this.ConvertData[1]=Integer.parseInt(y.substring(y.length()-3, y.length()-2));	
			}

		}
		else {
			this.ConvertData[1]=Integer.parseInt("00");
		}	
		if(b>0){
			if(b>16) {
				this.ConvertData[2]=Integer.parseInt(y.substring(y.length()-2, y.length()),16);	
			}
			else {
				this.ConvertData[2]=Integer.parseInt(y.substring(y.length()-1,y.length()));
			}
		}
		else {
			this.ConvertData[2]=Integer.parseInt("00");
		}	
	}
	public void set(int x, int y,int val) {
		int p=0;
		this.translate(val);
		for(int j=0;j<this.height;j++) {
			for(int i=0;i<this.width;i++) {
				for(int count=0;count<3;count++) {
					if((i==x)&&(j==y)) {
						this.data[p]=(byte)this.ConvertData[count];	
					}
					p++;
				}
			}
		}
	}
	public void write(String filename) throws FileNotFoundException,IOException  {
		FileOutputStream file1 = new FileOutputStream(filename);
		DataOutputStream file_c = new DataOutputStream(file1);
		String w = Integer.toString(width);
		String h = Integer.toString(height);
		String content= "P6";
		String content2= "#by JASON";
		String content3= "256";
		char n='\n';
		char s=' ';
		char t='\t';
		file_c.writeBytes(content);
		file_c.writeByte(n);
		file_c.writeBytes(content2);
		file_c.writeByte(n);
		file_c.writeBytes(w);
		file_c.writeByte(s);
		file_c.writeBytes(h);
		file_c.writeByte(n);
		file_c.writeBytes(content3);
		file_c.writeByte(n);
		int p=0;
		for (int y=0;y<this.height;y++) {
			for(int x=0;x<this.width;x++) {
				for(int count=0;count<3;count++) {
					if(data[p]<10) {
						file_c.writeChars(Integer.toString(data[p]));	
					}
					else {
						file_c.writeBytes(Integer.toString(data[p]));	
					}
					file_c.writeByte(s);
					p++;
				}
				if(x<(this.width-1)) {
					file_c.writeByte(t);
				}
			}
			if(y<(this.height-1)) {
				file_c.writeByte(n);
			}
		}
		file1.flush();
		file1.close();
	}

}
