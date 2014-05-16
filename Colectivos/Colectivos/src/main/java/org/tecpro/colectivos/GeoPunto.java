package org.tecpro.colectivos;

public class GeoPunto {


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


}