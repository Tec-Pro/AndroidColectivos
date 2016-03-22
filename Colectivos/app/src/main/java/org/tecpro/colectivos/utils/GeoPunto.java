package org.tecpro.colectivos.utils;

import com.google.android.gms.maps.model.LatLng;

public class GeoPunto {
	private double Xaux1;
	private double Yaux1;
	private double Xaux2;
	private double Yaux2;

	/** Calcula la distancia de este punto a otro punto
	 * @return la distancia en metros.
	 */
	public double distancia(double pLongitud, double pLatitud, double p2Longitud, double p2Latitud) {
		final double RADIO_TIERRA = 6371000 ; // en metros
		double dLat = Math.toRadians(pLatitud - p2Latitud); /* en radianes, para usar en sin y cos */
		double dLong = Math.toRadians(pLongitud - p2Longitud);
		double a = Math.sin(dLat/2) *
				   Math.sin(dLat/2) + 
				   Math.sin(dLong/2) *
				   Math.sin(dLong/2) *
				   Math.cos(Math.toRadians(pLatitud)) *
				   Math.cos(Math.toRadians(p2Latitud));
		double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		return RADIO_TIERRA * c;
	}


	public boolean intersect(LatLng x, LatLng y,  double xC, double yC, double rad){
		double  X=xC; //centro
		double Y= yC; //centro
		double radio= rad;
		double Xp= x.longitude ;
		double Yp= x.latitude;

		double Xq= y.longitude;
		double Yq= y.latitude;

		double m = (Yq-Yp)/(Xq-Xp);
		//System.out.println("m:"+m);
		double c= Yq-m*Xq;
		//System.out.println("c:"+c);

		double a= m*m;
		//System.out.println("m²:"+a);

		double b= 2*m*(c-Y);
		//System.out.println("b:"+b);
		double cPol= (c-Y)*(c-Y);
		//System.out.println("c:"+cPol);
		cPol= cPol-(radio*radio)+(X*X);
		a=a+1;
		//System.out.println("a:"+a);
		b= b- 2*X;
		//System.out.println("b:"+b);
		//System.out.println("c:"+cPol);
		boolean ret=aplicarFormula(a, b, cPol);
		Yaux1=m*Xaux1+c;
		Yaux2=m*Xaux2+c;
		//System.out.println("y1= " +Yaux1);
		//System.out.println("y2= " +Yaux2);
		double limInf= Math.min(x.longitude,y.longitude);
		double limSup= Math.max(x.longitude,y.longitude);
		return (((limInf<=Xaux1&&Xaux1<=limSup)||(limInf<=Xaux2&&Xaux2<=limSup))&&ret);
	}


	public  boolean aplicarFormula(double a, double b, double c) {
		double x1 = (-b + Math.sqrt((b * b) - 4 * a * c)) / (2 * a);
		double x2 = (-b - Math.sqrt((b * b) - 4 * a * c)) / (2 * a);
		Xaux1=x1;
		Xaux2=x2;
		//System.out.println("El valor de x1 es: " + x1);
		//System.out.println("El valor de x2 es: " + x2);
		return(!Double.isNaN(x1)||!Double.isNaN(x2));
	}

    public double getXaux1() {
        return Xaux1;
    }

    public double getYaux1() {
        return Yaux1;
    }

    public double getXaux2() {
        return Xaux2;
    }

    public double getYaux2() {
        return Yaux2;
    }

    public LatLng puntoMedio( double pLongitud, double pLatitud, double p2Longitud, double p2Latitud){
        double medioLong=(pLongitud+p2Longitud)/2;
        double medioLat= (pLatitud+p2Latitud)/2;
        return new LatLng(medioLat,medioLong);
    }
}