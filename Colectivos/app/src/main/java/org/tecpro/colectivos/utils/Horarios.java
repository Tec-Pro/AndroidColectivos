package org.tecpro.colectivos.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by agustin on 11/04/14.
 */
public class Horarios{

    private String[] makeArray(String horarios){
        String l = horarios;
        String[] a;
        a = l.split("\\s");
        LinkedList<String> la = new LinkedList(Arrays.asList(a));
        Iterator it = la.iterator();
        while(it.hasNext()){
            String s = (String) it.next();

            if(s.equals("")){
                it.remove();
            }
        }
        String[] c = la.toArray(new String[0]);
        return c;
    }

    private static String[] parserHorario(String horario){
        String parserAux= horario.replace("  \t","--:-- \t");
        parserAux = parserAux.replace("\n"," \t");
        return parserAux.split(" \t");
    }

    private static String[] parserHorarioVarios(int cantCols,String[]...params){
        ArrayList<String> retAux = new ArrayList<>();
        int ciclo =1;
        int position;
        int maxSize=0;
        for (String[]array: params){
            maxSize = maxSize+ array.length;
        }
        while(retAux.size()<maxSize){
            for(int i=0; i<params.length; i++){
                position = (ciclo-1)*cantCols;
                for(int j=0; j<cantCols; j++){
                    if(position+j<params[i].length)
                        retAux.add(params[i][position+j]);
                }
            }
            ciclo++;
        }
        String []dsf = new String[retAux.size()];
        retAux.toArray(dsf);
        return dsf;
    };

    private String[] parserBusStops(String busStops){
        return busStops.split(" \t");
    }

    private String busStops1verde ="Va. Dalcar \tPlaza \tAlberdi \tPlaza";
    private String busStops1verdeFinde ="Tanque \tPlaza \tBº Peyr \tPlaza \tCement \tV. Dalcar";
    private String timeTable1verde =
            "05:15 \t05:23 \t05:32 \t05:43\n" +
                    "  \t  \t06:20 \t06:37\n" +
                    "06:00 \t06:18 \t06:37 \t06:55\n" +
                    "06:25 \t06:42 \t07:01 \t07:20\n" +
                    "06:48 \t07:07 \t07:27 \t07:50\n" +
                    "07:15 \t07:33 \t07:53 \t08:16\n" +
                    "07:40 \t07:58 \t08:21 \t09:44\n" +
                    "08:10 \t08:27 \t08:50 \t09:13\n" +
                    "08:36 \t09:55 \t09:18 \t09:41\n" +
                    "09:04 \t09:23 \t09:46 \t10:09\n" +
                    "09:33 \t09:51 \t10:14 \t10:37\n" +
                    "10:01 \t10:19 \t10:42 \t11:05\n" +
                    "10:29 \t10:47 \t11:10 \t11:33\n" +
                    "10:57 \t11:15 \t11:38 \t12:01\n" +
                    "11:25 \t11:43 \t12:06 \t12:29\n" +
                    "11:53 \t12:11 \t12:34 \t12:57\n" +
                    "12:21 \t12:39 \t13:02 \t13:25\n" +
                    "12:49 \t13:07 \t13:30 \t13:53\n" +
                    "13:17 \t13:35 \t13:58 \t14:21\n" +
                    "13:45 \t12:03 \t14:26 \t14:49\n" +
                    "14:13 \t14:31 \t14:54 \t15:17\n" +
                    "14:41 \t14:59 \t15:22 \t15:45\n" +
                    "15:09 \t15:27 \t15:50 \t16:13\n" +
                    "15:37 \t15:55 \t16:18 \t16:41\n" +
                    "18:05 \t16:23 \t16:46 \t17:09\n" +
                    "13:33 \t16:51 \t17:14 \t17:37\n" +
                    "17:01 \t17:19 \t17:42 \t18:05\n" +
                    "17:29 \t17:47 \t18:10 \t18:33\n" +
                    "17:57 \t18:15 \t18:38 \t19:01\n" +
                    "18:25 \t18:43 \t19:06 \t19:29\n" +
                    "18:53 \t19:11 \t19:34 \t19:57\n" +
                    "19:21 \t19:39 \t20:02 \t20:25\n" +
                    "19:49 \t20:07 \t20:27 \t20:47\n" +
                    "20:17 \t20:35 \t20:55 \t21:15\n" +
                    "20:45 \t21:03 \t21:14 \t21:44\n" +
                    "21:07 \t21:25 \t21:45 \t22:05\n" +
                    "22:00 \t22:12 \t22:29 \t22:44\n" +
                    "23:00 \t23:12 \t23:29 \t23:44\n" +
                    "00:00 \t00:12 \t00:29 \t00:44";
    private String timeTable1verdeE =
            "22:00 \t22:12 \t22:29 \t22:44\n" +
                    "23:00 \t23:12 \t23:29 \t23:44\n" +
                    "00:00 \t00:12 \t00:29 \t00:44\n" +
                    "01:00 \t  \t  \t ";
    private String timeTable1verdeFinde1 =
            "  \t  \t  \t  \t  \t05:15\n" +
                    "05:19 \t05:23 \t05:32 \t05:43 \t05:51 \t06:00\n" +
                    "06:10 \t06:18 \t06:37 \t06:55 \t07:05 \t07:15\n" +
                    "07:25 \t07:33 \t07:53 \t08:13 \t08:23 \t08:33\n" +
                    "08:43 \t08:51 \t09:11 \t09:31 \t09:41 \t09:51\n" +
                    "10:01 \t10:09 \t10:29 \t10:49 \t10:59 \t11:09\n" +
                    "11:19 \t11:27 \t11:47 \t12:07 \t12:17 \t12:27\n" +
                    "12:37 \t12:45 \t13:05 \t13:25 \t13:35 \t13:45\n" +
                    "13:55 \t14:03 \t14:23 \t14:43 \t14:53 \t15:03\n" +
                    "15:13 \t15:21 \t15:41 \t16:01 \t16:11 \t16:21\n" +
                    "16:31 \t16:39 \t16:59 \t17:19 \t17:29 \t17:39\n" +
                    "17:49 \t17:57 \t18:17 \t18:37 \t18:47 \t18:57\n" +
                    "19:07 \t19:15 \t19:35 \t19:55 \t20:05 \t20:15\n" +
                    "20:25 \t20:33 \t20:53 \t21:13 \t21:23 \t21:30";
    private String timeTable1verdeFinde2 =
            "  \t  \t  \t  \t  \t06:24\n" +
                    "06:35 \t06:42 \t07:01 \t07:20 \t07:30 \t07:40\n" +
                    "07:51 \t07:59 \t08:19 \t08:39 \t08:49 \t08:59\n" +
                    "09:09 \t09:17 \t09:37 \t09:57 \t10:07 \t10:17\n" +
                    "10:27 \t10:35 \t10:55 \t11:15 \t11:25 \t11:35\n" +
                    "11:45 \t11:53 \t12:13 \t12:33 \t12:43 \t12:53\n" +
                    "13:03 \t13:11 \t13:31 \t13:51 \t14:01 \t14:11\n" +
                    "14:21 \t14:29 \t14:49 \t15:09 \t15:19 \t15:29\n" +
                    "15:39 \t15:47 \t16:07 \t16:27 \t16:28 \t16:47\n" +
                    "16:57 \t17:05 \t17:25 \t17:45 \t17:55 \t18:05\n" +
                    "18:15 \t18:23 \t18:43 \t19:03 \t19:13 \t19:23\n" +
                    "19:33 \t19:41 \t20:01 \t20:21 \t20:31 \t20:41\n" +
                    "20:51 \t20:59 \t21:19 \t21:39 \t21:49 \t21:56";
    private String timeTable1verdeFinde3 =
            "  \t  \t06:19 \t06:37 \t06:42 \t06:48\n" +
                    "06:59 \t07:07 \t07:27 \t07:47 \t07:57 \t08:07\n" +
                    "08:18 \t08:25 \t08:45 \t09:05 \t09:15 \t09:25\n" +
                    "09:35 \t09:43 \t10:03 \t10:23 \t10:33 \t10:43\n" +
                    "10:53 \t11:01 \t11:21 \t11:41 \t11:51 \t12:01\n" +
                    "12:11 \t12:19 \t12:39 \t12:59 \t13:09 \t13:19\n" +
                    "13:29 \t13:37 \t13:57 \t14:17 \t14:27 \t14:37\n" +
                    "14:47 \t14:55 \t15:15 \t15:35 \t15:45 \t15:55\n" +
                    "16:05 \t16:13 \t16:33 \t16:53 \t17:03 \t17:13\n" +
                    "17:23 \t17:31 \t17:51 \t18:11 \t18:21 \t18:31\n" +
                    "18:41 \t18:49 \t19:09 \t19:29 \t19:39 \t19:49\n" +
                    "19:59 \t20:07 \t20:27 \t20:47 \t20:57 \t21:07\n" +
                    "21:17 \t21:25 \t21:45 \t22:05 \t22:13 \t22:20";

    public String[] getTimeTable1verde(){
        return parserHorario(timeTable1verde);
    }

    public String[] getTimeTable1verdeE(){
        return parserHorario(timeTable1verdeE);
    }

    public String[] getTimeTable1verdeSyD(){
        return parserHorarioVarios(parserBusStops(busStops1verdeFinde).length, parserHorario(timeTable1verdeFinde1), parserHorario(timeTable1verdeFinde2), parserHorario(timeTable1verdeFinde3));
    }

    public String[] getBusStops1verdeSyD(){
        return parserBusStops(busStops1verdeFinde);
    }

    public String[] getBusStops1verde(){
        return parserBusStops(busStops1verde);
    }


    /**
     * LINEA 1 VERDE
     *
     *
     *
     *
     */


    private String busStops1rojo ="Golf \tPlaza \tBº Peyrano \tPlaza";
    private String busStops1rojoFinde ="Tanque \tPlaza \tBº Peyr \tPlaza \tCement \tGolf";
    private String timeTable1rojo =
            "  \t  \t05:20 \t05:32\n" +
                    "05:45 \t05:55 \t06:10 \t06:25\n" +
                    "  \t06:09 \t06:26 \t06:44\n" +
                    "06:12 \t06:30 \t06:49 \t07:07\n" +
                    "06:35 \t06:54 \t07:14 \t07:35\n" +
                    "07:00 \t07:20 \t07:40 \t08:00\n" +
                    "07:27 \t07:46 \t08:08 \t08:31\n" +
                    "07:55 \t08:13 \t08:36 \t08:59\n" +
                    "08:23 \t08:41 \t09:04 \t09:27\n" +
                    "08:51 \t09:09 \t09:32 \t09:55\n" +
                    "09:19 \t09:37 \t10:00 \t10:23\n" +
                    "09:47 \t10:05 \t10:28 \t10:51\n" +
                    "10:15 \t10:33 \t10:56 \t11:19\n" +
                    "10:43 \t11:01 \t11:24 \t11:47\n" +
                    "11:11 \t11:29 \t11:52 \t12:15\n" +
                    "11:39 \t11:57 \t12:20 \t12:43\n" +
                    "12:07 \t12:25 \t12:48 \t13:11\n" +
                    "12:35 \t12:53 \t13:16 \t13:39\n" +
                    "13:03 \t13:21 \t13:44 \t14:07\n" +
                    "13:31 \t13:49 \t14:12 \t14:35\n" +
                    "13:59 \t14:17 \t14:40 \t15:03\n" +
                    "14:27 \t14:45 \t15:08 \t15:31\n" +
                    "14:55 \t15:13 \t15:36 \t15:59\n" +
                    "15:23 \t15:41 \t16:04 \t16:27\n" +
                    "15:51 \t16:09 \t16:32 \t16:55\n" +
                    "16:19 \t16:37 \t17:00 \t17:23\n" +
                    "16:47 \t17:05 \t17:28 \t17:51\n" +
                    "17:15 \t17:33 \t17:56 \t18:19\n" +
                    "17:43 \t18:01 \t18:24 \t18:47\n" +
                    "18:11 \t18:29 \t18:52 \t19:15\n" +
                    "18:39 \t18:57 \t19:20 \t19:43\n" +
                    "19:07 \t19:25 \t19:48 \t20:11\n" +
                    "19:35 \t19:53 \t20:16 \t20:39\n" +
                    "20:03 \t20:21 \t20:41 \t21:01\n" +
                    "20:31 \t20:49 \t21:10 \t21:30\n" +
                    "20:59 \t21:17 \t21:37 \t21:57\n" +
                    "21:30 \t21:42 \t21:59 \t22:14\n" +
                    "22:30 \t22:42 \t22:59 \t23:14\n" +
                    "23:30 \t23:42 \t23:59 \t00:14";
    private String timeTable1rojoE =
            "21:30 \t21:42 \t21:59 \t22:14\n" +
                    "22:30 \t22:42 \t22:59 \t23:14\n" +
                    "23:30 \t23:42 \t23:59 \t00:14\n" +
                    "00:30 \t  \t  \t ";
    private String timeTable1rojoFinde1 =
            "  \t  \t05:20 \t05:35 \t05:40 \t05:45\n" +
                    "05:50 \t05:55 \t06:10 \t06:25 \t06:30 \t06:35\n" +
                    "06:46 \t06:54 \t07:14 \t07:34 \t07:44 \t07:54\n" +
                    "08:04 \t08:12 \t08:32 \t08:52 \t09:02 \t09:12\n" +
                    "09:22 \t09:30 \t09:50 \t10:10 \t10:20 \t10:30\n" +
                    "10:40 \t10:48 \t11:08 \t11:28 \t11:38 \t11:48\n" +
                    "13:55 \t12:06 \t12:26 \t12:46 \t12:56 \t13:06\n" +
                    "13:16 \t13:24 \t13:44 \t14:04 \t14:14 \t14:24\n" +
                    "14:34 \t14:42 \t15:02 \t15:22 \t14:32 \t15:42\n" +
                    "15:52 \t16:00 \t16:20 \t16:40 \t16:50 \t17:00\n" +
                    "17:10 \t17:18 \t17:38 \t17:58 \t18:08 \t18:18\n" +
                    "18:28 \t18:36 \t18:56 \t19:16 \t19:26 \t19:36\n" +
                    "19:46 \t19:54 \t20:14 \t20:34 \t20:44 \t20:54\n" +
                    "21:04 \t21:12 \t21:32 \t21:52 \t22:02 \t22:10";
    private String timeTable1rojoFinde2 =
            "  \t  \t  \t  \t  \t06:12\n" +
                    "06:22 \t06:30 \t06:49 \t07:07 \t07:17 \t07:27\n" +
                    "07:38 \t07:46 \t08:06 \t08:26 \t08:36 \t08:46\n" +
                    "08:56 \t09:04 \t09:24 \t09:44 \t09:55 \t10:04\n" +
                    "10:14 \t10:22 \t10:42 \t11:02 \t11:12 \t11:22\n" +
                    "11:32 \t11:40 \t12:00 \t12:20 \t12:30 \t12:40\n" +
                    "12:50 \t12:58 \t13:18 \t13:38 \t13:48 \t13:58\n" +
                    "14:08 \t14:16 \t14:36 \t14:56 \t15:06 \t15:16\n" +
                    "15:26 \t15:34 \t15:54 \t16:14 \t16:24 \t16:34\n" +
                    "16:44 \t16:52 \t17:12 \t17:32 \t17:42 \t17:52\n" +
                    "18:02 \t18:10 \t18:30 \t18:50 \t19:00 \t19:10\n" +
                    "19:20 \t19:28 \t19:48 \t20:08 \t20:18 \t20:28\n" +
                    "20:38 \t20:46 \t21:06 \t21:26 \t21:36 \t21:43";
    private String timeTable1rojoFinde3 =
            "  \t06:09 \t06:26 \t06:44 \t06:52 \t07:00\n" +
                    "07:12 \t07:20 \t07:40 \t08:00 \t08:10 \t08:20\n" +
                    "08:30 \t08:38 \t08:58 \t09:18 \t09:28 \t09:38\n" +
                    "09:48 \t09:56 \t10:16 \t10:36 \t10:46 \t10:56\n" +
                    "11:06 \t11:14 \t11:34 \t11:54 \t12:04 \t12:14\n" +
                    "12:24 \t12:32 \t12:52 \t13:12 \t13:22 \t13:32\n" +
                    "13:42 \t13:50 \t14:10 \t14:30 \t14:40 \t14:50\n" +
                    "15:00 \t15:08 \t15:28 \t15:48 \t15:58 \t16:08\n" +
                    "16:18 \t16:26 \t16:46 \t17:06 \t17:16 \t17:26\n" +
                    "17:36 \t17:44 \t18:04 \t18:24 \t18:34 \t18:44\n" +
                    "18:54 \t19:02 \t19:22 \t19:42 \t19:52 \t20:02\n" +
                    "20:12 \t20:20 \t20:40 \t21:00 \t21:10 \t21:20";

    public String[] getTimeTable1rojo(){
        return parserHorario(timeTable1rojo);
    }

    public String[] getTimeTable1rojoE(){
        return parserHorario(timeTable1rojoE);
    }

    public String[] getTimeTable1rojoSyD(){
        return parserHorarioVarios(parserBusStops(busStops1rojoFinde).length, parserHorario(timeTable1rojoFinde1), parserHorario(timeTable1rojoFinde2), parserHorario(timeTable1rojoFinde3));
    }

    public String[] getBusStops1rojoSyD(){
        return parserBusStops(busStops1rojoFinde);
    }

    public String[] getBusStops1rojo(){
        return parserBusStops(busStops1rojo);
    }


    /**
     * linea 2 rojo
     *
     *
     *
     */
    private String busStops2rojo =     "MORET \tEPEC  \tR.SEM \tUNRC \tPARQ \tPLAZA \tCARC \tL.FERI";
    private String timeTable2rojo1 =
            "  \t  \t  \t  \t  \t  \t  \t5:25\n" +
                    "5:35 \t5:47 \t6:03 \t6:11 \t6:22 \t6:31 \t6:40 \t6:49\n" +
                    "7:00 \t7:14 \t7:31 \t7:37 \t7:50 \t8:00 \t8:08 \t8:04\n" +
                    "8:16 \t8:34 \t8:53 \t9:03 \t9:21 \t9:32 \t9:42 \t9:53\n" +
                    "10:05 \t10:23 \t10:42 \t10:52 \t11:10 \t11:21 \t11:31 \t11:42\n" +
                    "11:54 \t12:12 \t12:31 \t12:41 \t12:59 \t13:10 \t13:20 \t13:31\n" +
                    "13:43 \t14:01 \t14:20 \t14:30 \t14:48 \t14:59 \t15:09 \t15:20\n" +
                    "15:32 \t15:50 \t16:09 \t16:19 \t16:37 \t17:07 \t17:17 \t17:25\n" +
                    "17:42 \t18:00 \t18:19 \t18:29 \t18:47 \t18:58 \t19:08 \t19:19\n" +
                    "19:31 \t19:49 \t20:08 \t20:18 \t20:36 \t20:47 \t20:57 \t21:08\n" +
                    "21:20 \t21:38 \t21:57 \t22:00 \t22:10 \t22:20 \t22:25 \t22:30";
    private String timeTable2rojo2 =
            "  \t  \t  \t5:40 \t5:50 \t5:55 \t6:04 \t \n" +
                    "  \t  \t  \t  \t  \t  \t  \t7:20\n" +
                    "7:31 \t7:45 \t8:02 \t8:11 \t8:27 \t8:38 \t8:48 \t0:12\n" +
                    "0:24 \t0:42 \t1:01 \t1:11 \t1:29 \t1:40 \t1:50 \t2:01\n" +
                    "2:13 \t2:31 \t2:50 \t3:00 \t3:18 \t3:29 \t3:39 \t3:50\n" +
                    "4:02 \t4:20 \t4:39 \t4:49 \t5:07 \t5:18 \t5:28 \t5:39\n" +
                    "5:51 \t6:09 \t6:28 \t6:38 \t6:56 \t7:07 \t7:17 \t7:28\n" +
                    "7:40 \t7:58 \t8:17 \t8:27 \t8:45 \t8:56 \t9:06 \t9:17\n" +
                    "9:29 \t9:47 \t10:06 \t10:16 \t10:34 \t10:45 \t10:55 \t11:06\n" +
                    "11:18 \t11:36 \t11:55 \t12:05 \t12:23 \t12:34 \t12:44 \t12:55\n" +
                    "21:52 \t22:05 \t22:20 \t22:25 \t  \t  \t  \t ";
    private String timeTable2rojo3 =
            "  \t  \t  \t  \t  \t  \t  \t     6:20\n" +
                    "6:30 \t6:44 \t7:01 \t7:10 \t7:22 \t7:32 \t7:40 \t  A 7:51\n" +
                    "8:04 \t8:19 \t8:36 \t8:46 \t9:03 \t9:13 \t9:20 \t8:03\n" +
                    "8:15 \t8:33 \t8:52 \t9:02 \t9:20 \t9:31 \t9:41 \t9:52\n" +
                    "10:04 \t10:22 \t10:41 \t10:51 \t11:09 \t11:20 \t11:30 \t11:41\n" +
                    "11:53 \t12:11 \t12:30 \t12:40 \t12:58 \t13:09 \t13:19 \t13:30\n" +
                    "13:42 \t14:00 \t14:19 \t14:29 \t14:47 \t14:58 \t15:08 \t15:19\n" +
                    "15:31 \t15:49 \t16:08 \t16:18 \t16:36 \t16:47 \t16:57 \t17:08\n" +
                    "17:20 \t17:38 \t17:57 \t18:07 \t18:25 \t18:36 \t18:46 \t18:57\n" +
                    "19:09 \t19:27 \t19:46 \t19:56 \t20:14 \t20:25 \t20:35 \t20:46";


    private String timeTable2rojoFinde1 =
            "  \t  \t  \t  \t  \t  \t  \t5:10\n" +
                    "5:14 \t5:25 \t5:34 \t5:40 \t5:50 \t5:55 \t6:01 \t6:15\n" +
                    "06:25 \t06:40 \t06:55 \t07:44 \t07:18 \t07:26 \t07:35 \t07:45\n" +
                    "07:55 \t08:10 \t08:25 \t08:34 \t08:48 \t08:56 \t09:05 \t09:15\n" +
                    "09:25 \t09:40 \t09:55 \t10:04 \t10:18 \t10:26 \t10:35 \t10:45\n" +
                    "10:55 \t11:10 \t11:25 \t11:34 \t11:48 \t11:56 \t12:05 \t12:15\n" +
                    "12:25 \t12:40 \t12:55 \t13:04 \t13:18 \t13:26 \t13:35 \t13:45\n" +
                    "13:55 \t14:10 \t14:25 \t14:34 \t14:48 \t14:56 \t15:05 \t15:15\n" +
                    "15:25 \t15:40 \t15:55 \t16:04 \t16:18 \t16:26 \t16:35 \t16:45\n" +
                    "16:55 \t17:10 \t17:25 \t17:34 \t17:48 \t17:56 \t18:05 \t18:15\n" +
                    "18:25 \t18:40 \t18:55 \t19:04 \t19:18 \t19:26 \t19:35 \t19:45\n" +
                    "19:55 \t20:10 \t20:25 \t20:34 \t20:48 \t20:56 \t21:05 \t21:15";
    private String timeTable2rojoFinde2 =
            "  \t  \t  \t  \t  \t  \t  \t5:35\n" +
                    "5:43 \t5:55 \t6:07 \t6:15 \t6:27 \t6:35 \t6:43 \t6:56\n" +
                    "07:06 \t07:21 \t07:36 \t07:45 \t07:59 \t08:07 \t08:16 \t08:26\n" +
                    "08:40 \t08:55 \t09:10 \t09:19 \t09:33 \t09:41 \t09:50 \t10:00\n" +
                    "10:10 \t10:25 \t10:40 \t10:49 \t11:03 \t11:11 \t11:20 \t11:30\n" +
                    "11:40 \t11:55 \t12:10 \t12:19 \t12:33 \t12:41 \t12:50 \t13:00\n" +
                    "13:10 \t13:25 \t13:40 \t13:49 \t14:03 \t14:11 \t14:20 \t14:30\n" +
                    "14:40 \t14:55 \t15:10 \t15:19 \t15:33 \t15:41 \t15:50 \t16:00\n" +
                    "16:10 \t16:25 \t16:40 \t16:49 \t17:03 \t17:11 \t17:20 \t17:30\n" +
                    "17:40 \t17:55 \t18:10 \t18:19 \t18:33 \t18:41 \t18:50 \t19:00\n" +
                    "19:10 \t19:25 \t19:40 \t19:49 \t20:03 \t20:11 \t20:20 \t20:30";


    public String[] getTimeTable2rojo(){
        return parserHorarioVarios(parserBusStops(busStops2rojo).length, parserHorario(timeTable2rojo1), parserHorario(timeTable2rojo2), parserHorario(timeTable2rojo3));
    }



    public String[] getTimeTable2rojoSyD(){
        return parserHorarioVarios(parserBusStops(busStops2rojo).length, parserHorario(timeTable2rojoFinde1), parserHorario(timeTable2rojoFinde2));
    }


    public String[] getBusStops2rojo() {
        return parserBusStops(busStops2rojo);
    }

    /**
     * 2 verde
     */


    /**
     * linea 2 rojo
     *
     *
     *
     */
    private String busStops2verde =     "MORET \tEPEC \tR.SEM \tUNRC \tPARQ \tPLAZA \tCARC \tH.CENT";
    private String busStops2verdeEspecial =     "EPEC \tUNRC \tPlaza \tH. Central";

    private String timeTable2verde1 =
            "  \t  \t  \t  \t  \t  \t  \t4:50\n" +
                    "4:55 \t5:05 \t5:20 \t5:30 \t5:37 \t5:45 \t5:54 \t6:10\n" +
                    "6:17 \t6:30 \t6:51 \t7:00 \t7:12 \t7:22 \t7:31 \t7:40\n" +
                    "7:52 \t7:58 \t7:59 \t7:50 \t7:58 \t7:51 \t7:50 \t7:51\n" +
                    "8:03 \t8:21 \t8:40 \t8:50 \t9:08 \t9:19 \t9:29 \t9:40\n" +
                    "9:52 \t10:10 \t10:29 \t10:39 \t10:57 \t11:08 \t11:18 \t11:29\n" +
                    "11:41 \t11:59 \t12:18 \t12:28 \t12:46 \t12:57 \t13:07 \t13:18\n" +
                    "13:30 \t13:48 \t14:07 \t14:17 \t14:35 \t14:46 \t14:56 \t15:07\n" +
                    "15:19 \t15:37 \t15:56 \t16:06 \t16:24 \t16:35 \t16:45 \t16:56\n" +
                    "17:08 \t17:26 \t17:45 \t17:55 \t18:13 \t18:24 \t18:34 \t18:45\n" +
                    "18:57 \t19:15 \t19:34 \t19:44 \t20:02 \t20:13 \t20:23 \t20:34";
    private String timeTable2verde2 =
            "  \t  \t  \t  \t  \t  \t  \t5:10\n" +
                    "5:20 \t5:34 \t5:49 \t5:58 \t6:10 \t6:22 \t6:31 \t6:40\n" +
                    "6:50 \t7:04 \t7:21 \t7:30 \t7:42 \t7:52 \t8:01 \t7:52\n" +
                    "8:04 \t8:22 \t8:41 \t8:51 \t9:09 \t9:20 \t9:30 \t9:41\n" +
                    "9:53 \t10:11 \t10:30 \t10:40 \t10:58 \t11:09 \t11:19 \t11:30\n" +
                    "11:42 \t12:00 \t12:19 \t12:29 \t12:47 \t12:58 \t13:08 \t13:19\n" +
                    "13:31 \t13:49 \t14:08 \t14:18 \t14:36 \t14:47 \t14:57 \t15:08\n" +
                    "15:20 \t15:38 \t15:57 \t16:07 \t16:25 \t16:36 \t16:46 \t16:57\n" +
                    "17:09 \t17:27 \t17:46 \t17:56 \t18:14 \t18:25 \t18:35 \t18:46\n" +
                    "18:58 \t19:16 \t19:35 \t19:45 \t20:03 \t20:14 \t20:24 \t20:35\n" +
                    "20:47 \t21:05 \t21:24 \t21:52 \t22:03 \t22:14 \t22:18 \t22:25";
    private String timeTable2verde3 =
            "  \t  \t  \t  \t  \t  \t  \t5:45\n" +
                    "5:55 \t6:05 \t6:20 \t6:28 \t6:39 \t6:45 \t6:54 \t7:00\n" +
                    "7:11 \t7:24 \t7:41 \t7:50 \t8:02 \t8:12 \t8:21 \t8:16\n" +
                    "8:28 \t8:46 \t9:05 \t9:15 \t9:33 \t9:44 \t9:54 \t10:05\n" +
                    "10:17 \t10:35 \t10:54 \t11:04 \t11:22 \t11:33 \t11:43 \t11:54\n" +
                    "12:06 \t12:24 \t12:43 \t12:53 \t13:11 \t13:22 \t13:32 \t13:43\n" +
                    "13:55 \t14:13 \t14:32 \t14:42 \t15:00 \t15:11 \t15:21 \t15:32\n" +
                    "15:44 \t16:02 \t16:21 \t16:31 \t16:49 \t17:00 \t17:10 \t17:21\n" +
                    "17:33 \t17:51 \t18:10 \t18:20 \t18:38 \t18:49 \t18:59 \t19:10\n" +
                    "19:22 \t19:40 \t19:59 \t20:09 \t20:27 \t20:38 \t20:48 \t20:59\n" +
                    "21:11 \t21:29 \t21:48 \t22:16";
    private String timeTable2verde4 =
            "  \t  \t  \t  \t  \t  \t  \t6:00\n" +
                    "6:06 \t6:20 \t6:29 \t6:38 \t6:48 \t6:55 \t7:02 \t7:10\n" +
                    "7:22 \t7:37 \t7:54 \t8:03 \t8:19 \t8:33 \t8:43 \t8:28\n" +
                    "8:40 \t8:58 \t9:17 \t9:27 \t9:45 \t9:56 \t10:06 \t10:17\n" +
                    "10:29 \t10:47 \t11:06 \t11:16 \t11:34 \t11:45 \t11:55 \t12:06\n" +
                    "12:18 \t12:36 \t12:55 \t13:05 \t13:23 \t13:34 \t13:44 \t13:55\n" +
                    "14:07 \t14:25 \t14:44 \t14:54 \t15:12 \t15:23 \t15:33 \t15:44\n" +
                    "15:56 \t16:14 \t16:33 \t16:43 \t17:01 \t17:12 \t17:22 \t17:33\n" +
                    "17:45 \t18:03 \t18:22 \t18:32 \t18:50 \t19:01 \t19:11 \t19:22\n" +
                    "19:34 \t19:52 \t20:11 \t20:21 \t20:39 \t20:50 \t21:00 \t21:11";
    private String timeTable2verde6 =
            "  \t  \t  \t6:50 \t7:02 \t7:10 \t7:19 \t7:30\n" +
                    "7:42 \t7:56 \t8:13 \t8:22 \t8:38 \t8:49 \t8:58 \t8:52\n" +
                    "9:04 \t9:22 \t9:41 \t9:51 \t10:09 \t10:20 \t10:30 \t10:41\n" +
                    "10:53 \t11:11 \t11:30 \t11:40 \t11:58 \t12:09 \t12:19 \t12:30\n" +
                    "12:42 \t13:00 \t13:19 \t13:29 \t13:47 \t13:58 \t14:08 \t14:19\n" +
                    "14:31 \t14:49 \t15:08 \t15:18 \t15:36 \t15:47 \t15:57 \t16:08\n" +
                    "16:20 \t16:38 \t16:57 \t17:07 \t17:25 \t17:36 \t17:46 \t17:57\n" +
                    "18:09 \t18:27 \t18:46 \t18:56 \t19:14 \t19:25 \t19:35 \t19:46\n" +
                    "19:58 \t20:16 \t20:35 \t20:45 \t21:03 \t21:14 \t21:24 \t21:35";

    private String timeTable2verde5 =
            "  \t  \t  \t  \t  \t  \t  \t6:30\n" +
                    "6:40 \t6:54 \t7:11 \t7:20 \t7:32 \t7:42 \t7:51 \t8:02\n" +
                    "8:14 \t8:31 \t8:48 \t8:58 \t9:15 \t8:25 \t9:34 \t8:15\n" +
                    "8:27 \t8:45 \t9:04 \t9:14 \t9:32 \t9:43 \t9:53 \t10:04\n" +
                    "10:16 \t10:34 \t10:53 \t11:03 \t11:21 \t11:32 \t11:42 \t11:53\n" +
                    "12:05 \t12:23 \t12:42 \t12:52 \t13:10 \t13:21 \t13:31 \t13:42\n" +
                    "13:54 \t14:12 \t14:31 \t14:41 \t14:59 \t15:10 \t15:20 \t15:31\n" +
                    "15:43 \t16:01 \t16:20 \t16:30 \t16:48 \t16:59 \t17:09 \t17:20\n" +
                    "17:32 \t17:50 \t18:09 \t18:19 \t18:37 \t18:48 \t18:58 \t19:09\n" +
                    "19:21 \t19:39 \t19:58 \t20:08 \t20:26 \t20:37 \t20:47 \t20:58";

    private String timeTable2verdeFinde1 =
            "  \t  \t  \t  \t  \t  \t  \t4:50\n" +
                    "4:55 \t5:05 \t5:31 \t5:30 \t5:39 \t5:45 \t5:52 \t6:00\n" +
                    "06.10 \t06.25 \t06.40 \t06.49 \t07.03 \t07.11 \t07.20 \t07.30\n" +
                    "07.40 \t07.55 \t08.10 \t08.19 \t08.33 \t08.41 \t08.50 \t09.00\n" +
                    "09.10 \t09.25 \t09.40 \t09.49 \t10.03 \t10.11 \t10.20 \t10.30\n" +
                    "10.40 \t10.55 \t11.10 \t11.19 \t11.33 \t11.41 \t11.50 \t12.00\n" +
                    "12.10 \t12.25 \t12.40 \t12.49 \t13.03 \t13.11 \t13.20 \t13.30\n" +
                    "13.40 \t13.55 \t14.10 \t14.19 \t14.33 \t14.41 \t14.50 \t15.00\n" +
                    "15.10 \t15.25 \t15.40 \t15.49 \t16.03 \t16.11 \t16.20 \t16.30\n" +
                    "16.40 \t16.55 \t17.10 \t17.19 \t17.33 \t17.41 \t17.50 \t18.00\n" +
                    "18.10 \t18.25 \t18.40 \t18.49 \t19.03 \t19.11 \t19.20 \t19.30\n" +
                    "19.40 \t19.55 \t20.10 \t20.19 \t20.33 \t20.41 \t20.50 \t21.00\n" +
                    "21.10 \t21.25 \t21:36 \t21:45 \t21:56 \t22:05 \t22:12 \t22:20";
    private String timeTable2verdeFinde2 =
            "5:31 \t5:40 \t5:52 \t6:00 \t6:12 \t6:20 \t6:24 \t6:28\n" +
                    "6:40 \t06.55 \t07.10 \t07.19 \t07.33 \t07.41 \t07.50 \t08.00\n" +
                    "08.10 \t08.25 \t08.40 \t08.49 \t09.03 \t09.11 \t09.20 \t09.30\n" +
                    "09.40 \t09.55 \t10.10 \t10.19 \t10.33 \t10.41 \t10.50 \t11.00\n" +
                    "11.10 \t11.25 \t11.40 \t11.49 \t12.03 \t12.11 \t12.20 \t12.30\n" +
                    "12.40 \t12.55 \t13.10 \t13.19 \t13.33 \t13.41 \t13.50 \t14.00\n" +
                    "14.10 \t14.25 \t14.40 \t14.49 \t15.03 \t15.11 \t15.20 \t15.30\n" +
                    "15.40 \t15.55 \t16.10 \t16.19 \t16.33 \t16.41 \t16.50 \t17.00\n" +
                    "17.10 \t17.25 \t17.40 \t17.49 \t18.03 \t18.11 \t18.20 \t18.30\n" +
                    "18.40 \t18.55 \t19.10 \t19.19 \t19.33 \t19.41 \t19.50 \t20.00\n" +
                    "20.10 \t20.25 \t20.40 \t20.49 \t21.03 \t21.11 \t21.20 \t21.30";
    private String timeTable2verdeFinde3 =
            "  \t  \t  \t  \t  \t  \t  \t6:42\n" +
                    "6:55 \t07.10 \t07.25 \t07.34 \t07.48 \t07.56 \t08.05 \t08.15\n" +
                    "8:25 \t08.40 \t08.55 \t09.04 \t09.18 \t09.26 \t09.35 \t09.45\n" +
                    "09.55 \t10.10 \t10.25 \t10.34 \t10.48 \t10.56 \t11.05 \t11.15\n" +
                    "11.25 \t11.40 \t11.55 \t12.04 \t12.18 \t12.26 \t12.35 \t12.45\n" +
                    "12.55 \t13.10 \t13.25 \t13.34 \t13.48 \t13.56 \t14.05 \t14.15\n" +
                    "14.25 \t14.40 \t14.55 \t15.04 \t15.18 \t15.26 \t15.35 \t15.45\n" +
                    "15.55 \t16.10 \t16.25 \t16.34 \t16.48 \t16.56 \t17.05 \t17.15\n" +
                    "17.25 \t17.40 \t17.55 \t18.04 \t18.18 \t18.26 \t18.35 \t18.45\n" +
                    "18.55 \t19.10 \t19.25 \t19.34 \t19.48 \t19.56 \t20.05 \t20.15\n" +
                    "20.25 \t20.40 \t20.55 \t21.04 \t21.18 \t21.26 \t21.35 \t21.45";
    private String timeTable2verdeFinde4 =
            "  \t  \t  \t  \t  \t  \t  \t5:50\n" +
                    "5:59 \t6:10 \t6:22 \t6:30 \t6:42 \t6:50 \t6:58 \t7:10\n" +
                    "07.20 \t07.35 \t07.50 \t07.59 \t08.13 \t08.21 \t08.30 \t08.40\n" +
                    "08.55 \t09.10 \t09.25 \t09.34 \t09.48 \t09.56 \t10.05 \t10.15\n" +
                    "10.25 \t10.40 \t10.55 \t11.04 \t11.18 \t11.26 \t11.35 \t11.45\n" +
                    "11.55 \t12.10 \t12.25 \t12.34 \t12.48 \t12.56 \t13.05 \t13.15\n" +
                    "13.25 \t13.40 \t13.55 \t14.04 \t14.18 \t14.26 \t14.35 \t14.45\n" +
                    "14.45 \t15.10 \t15.25 \t15.34 \t15.48 \t15.56 \t16.05 \t16.15\n" +
                    "16.25 \t16.40 \t16.55 \t17.04 \t17.18 \t17.26 \t17.35 \t17.45\n" +
                    "17.55 \t18.10 \t18.25 \t18.34 \t18.48 \t18.56 \t19.05 \t19.15\n" +
                    "19.25 \t19.40 \t19.55 \t20.04 \t20.18 \t20.26 \t20.35 \t20.45\n" +
                    "20.55 \t21.10 \t21.25 \t21.34 \t21.48 \t21.56 \t22.05 \t22.15";

    private String timeTable2verdeEspecial1 =
            "  \t  \t  \t21:30\n" +
                    "21:50 \t22:10 \t22:30 \t22:45\n" +
                    "23:05 \t23:25 \t23:45 \t00:00\n" +
                    "00:20 \t00:40 \t01:00 \t01:40";

    private String timeTable2verdeEspecial2 =
            "  \t  \t  \t21:55\n" +
                    "22:15 \t22:35 \t22:55 \t23:10\n" +
                    "23:30 \t23:50 \t00:10 \t00:25\n" +
                    "00:45 \t01:05 \t01:25 \t01:40";
    private String timeTable2verdeEspecial3 =
            "  \t  \t  \t22:20\n" +
                    "22:40 \t23:00 \t23:20 \t23:35\n" +
                    "23:55 \t00:15 \t00:35 \t00:50\n" +
                    "01:10 \t01:30 \t01:50 \t02:05";

    public String[] getTimeTable2verde(){
        return parserHorarioVarios(parserBusStops(busStops2verde).length, parserHorario(timeTable2verde1), parserHorario(timeTable2verde2), parserHorario(timeTable2verde3), parserHorario(timeTable2verde4), parserHorario(timeTable2verde5), parserHorario(timeTable2verde6));
    }



    public String[] getTimeTable2verdeSyD(){
        return parserHorarioVarios(parserBusStops(busStops2verde).length, parserHorario(timeTable2verdeFinde1), parserHorario(timeTable2verdeFinde2), parserHorario(timeTable2verdeFinde3), parserHorario(timeTable2verdeFinde4));
    }

    public String[] getTimeTable2verdeEspecial(){
        return parserHorarioVarios(parserBusStops(busStops2verdeEspecial).length, parserHorario(timeTable2verdeEspecial1), parserHorario(timeTable2verdeEspecial2), parserHorario(timeTable2verdeEspecial3));
    }


    public String[] getBusStops2verde() {
        return parserBusStops(busStops2verde);
    }

    public String[] getBusStops2verdeEspecial() {
        return parserBusStops(busStops2verdeEspecial);
    }


    /**
     * LINEA 3
     *
     *
     *
     */

    private String busStops3 ="Epec \tParque  \tL.Quin \tParque \tPlaza \tPlaza";
    private String busStops3E ="Epec \tL.Quin \tPlaza \tClínica";

    private String timeTable31 =
            "  \t  \t  \t  \t  \t5:15\n" +
                    "5:20 \t5:26 \t5:35 \t5:44 \t5:54 \t6:00\n" +
                    "6:07 \t6:26 \t6:32 \t6:44 \t6:54 \t7:00\n" +
                    "7:07 \t7:14 \t7:32 \t7:44 \t7:54 \t8:00\n" +
                    "8:07 \t8:14 \t8:32 \t8:44 \t8:54 \t9:00\n" +
                    "9:07 \t9:14 \t9:32 \t9:44 \t9:54 \t10:00\n" +
                    "10:07 \t10:14 \t10:32 \t10:44 \t10:54 \t11:00\n" +
                    "11:07 \t11:14 \t11:32 \t11:44 \t11:54 \t12:00\n" +
                    "12:07 \t12:14 \t12:32 \t12:44 \t12:54 \t13:00\n" +
                    "13:07 \t13:14 \t13:32 \t13:44 \t13:54 \t14:00\n" +
                    "14:07 \t14:14 \t14:32 \t14:44 \t14:54 \t15:00\n" +
                    "15:07 \t15:14 \t15:32 \t15:44 \t15:54 \t16:00\n" +
                    "16:07 \t16:14 \t16:32 \t16:44 \t16:54 \t17:00\n" +
                    "17:07 \t17:14 \t17:32 \t17:44 \t17:54 \t18:00\n" +
                    "18:07 \t18:14 \t18:32 \t18:44 \t18:54 \t19:00\n" +
                    "19:07 \t19:14 \t19:32 \t19:44 \t19:54 \t20:00\n" +
                    "20:07 \t20:14 \t20:32 \t20:44 \t20:54 \t21:00\n" +
                    "21:07 \t21:14 \t21:32 \t21:44 \t21:54 \t22:00";
    private String timeTable32 =
            "   \t  \t6:02 \t6:14 \t6:24 \t6:30\n" +
                    "6:37 \t6:47 \t7:02 \t7:14 \t7:24 \t7:30\n" +
                    "7:37 \t7:47 \t8:02 \t8:14 \t8:24 \t8:30\n" +
                    "8:37 \t8:47 \t9:02 \t9:14 \t9:24 \t9:30\n" +
                    "9:37 \t9:47 \t10:02 \t10:14 \t10:24 \t10:30\n" +
                    "10:37 \t10:47 \t11:02 \t11:14 \t11:24 \t11:30\n" +
                    "11:37 \t11:47 \t12:02 \t12:14 \t12:24 \t12:30\n" +
                    "12:37 \t12:47 \t13:02 \t13:14 \t13:24 \t13:30\n" +
                    "13:37 \t13:47 \t14:02 \t14:14 \t14:24 \t14:30\n" +
                    "14:37 \t14:47 \t15:02 \t15:14 \t15:24 \t15:30\n" +
                    "15:37 \t15:47 \t16:02 \t16:14 \t16:24 \t16:30\n" +
                    "16:37 \t16:47 \t17:02 \t17:14 \t17:24 \t17:30\n" +
                    "17:37 \t17:47 \t18:02 \t18:14 \t18:24 \t18:30\n" +
                    "18:37 \t18:47 \t19:02 \t19:14 \t19:24 \t19:30\n" +
                    "19:37 \t19:47 \t20:02 \t20:14 \t20:24 \t20:30\n" +
                    "20:37 \t20:47 \t21:02 \t21:14 \t21:24 \t21:30\n" +
                    "21:37 \t21:47 \t22:02 \t22:14 \t22:24 \t22:30";



    private String timeTable3E =
            "  \t  \t  \t22:00\n" +
                    "22:05 \t22:25 \t22:45 \t22:50\n" +
                    "22:55 \t23:15 \t23:35 \t23:40\n" +
                    "23:45 \t00:05 \t00:25 \t00:30\n" +
                    "00:35 \t00:55 \t01:15 \t01:20";

    public String[] getTimeTable3E(){
        return parserHorario(timeTable3E);
    }

    public String[] getTimeTable3(){
        return parserHorarioVarios(parserBusStops(busStops3).length, parserHorario(timeTable31), parserHorario(timeTable32));
    }

    public String[] getBusStops3() {
        return parserBusStops(busStops3);
    }

    public String[] getBusStops3E() {
        return parserBusStops(busStops3E);
    }


    /**
     *
     * LINEA 4
     */

    private String busStops4 ="PLAZA \tPILET. \tPLAZA  \tH.CENT. \tI.P.V.";

    private String timeTable4 =
            "  \t  \t  \t  \t5:30\n" +
                    "5:40 \t5:55 \t6:07 \t6:15 \t6:20\n" +
                    "6:37 \t6:49 \t7:05 \t7:15 \t7:20\n" +
                    "7:36 \t7:55 \t8:15 \t8:27 \t8:32\n" +
                    "8:46 \t9:09 \t9:32 \t9:47 \t9:50\n" +
                    "10:06 \t10:29 \t10:52 \t11:07 \t11:12\n" +
                    "11:26 \t11:49 \t12:09 \t12:27 \t12:32\n" +
                    "12:46 \t13:09 \t13:32 \t13:47 \t13:52\n" +
                    "14:06 \t14:29 \t14:52 \t15:07 \t15:12\n" +
                    "15:26 \t15:49 \t16:12 \t16:27 \t16:32\n" +
                    "16:46 \t17:09 \t17:32 \t17:47 \t17:52\n" +
                    "18:06 \t18:29 \t18:52 \t19:07 \t19:12\n" +
                    "19:26 \t19:49 \t20:12 \t20:27 \t20:32\n" +
                    "20:46 \t21:09 \t21:32 \t21:47 \t21:52";

    public String[] getTimeTable4(){
        return parserHorario(timeTable4);
    }

    public String[] getBusStops4() {
        return parserBusStops(busStops4);
    }


    /**
     *
     *
     *
     * linea 5
     *
     */

    private String busStops5 ="Plaza \tUNRC \tPlaza \tHipódromo \tComun";
    private String busStops5E ="Plaza \tUNRC \tPlaza \tBimaco";
    private String busStops5Finde ="PLAZA \tE:GAS \tUNRC \tE:GAS \tPLAZA \tHIPOD \tCOMUNI";

    private String timeTable5 =
            "  \t  \t  \t05:30 \t05:40\n" +
                    "  \t  \t  \t  \t06:15\n" +
                    "  \t  \t  \t06:25 \t06:30\n" +
                    "05:50 \t06:10 \t06:30 \t06:40 \t06:52\n" +
                    "  \t06:30 \t06:53 \t07:03 \t07:14\n" +
                    "06:20 \t06:45 \t07:10 \t07:24 \t07:39\n" +
                    "06:43 \t07:08 \t07:33 \t07:47 \t08:02\n" +
                    "07:04 \t07:29 \t07:54 \t08:08 \t08:23\n" +
                    "07:28 \t07:53 \t08:18 \t08:32 \t08:47\n" +
                    "07:51 \t08:16 \t08:41 \t08:55 \t09:10\n" +
                    "08:15 \t08:40 \t09:05 \t09:19 \t09:34\n" +
                    "08:38 \t09:03 \t09:28 \t09:42 \t09:57\n" +
                    "09:02 \t09:27 \t09:52 \t10:06 \t10:21\n" +
                    "09:25 \t09:50 \t10:15 \t10:29 \t10:44\n" +
                    "09:49 \t10:14 \t10:39 \t10:53 \t11:08\n" +
                    "10:12 \t10:37 \t11:02 \t11:16 \t11:31\n" +
                    "10:36 \t11:01 \t11:26 \t11:40 \t11:55\n" +
                    "10:59 \t11:24 \t11:49 \t12:03 \t12:18\n" +
                    "11:23 \t11:48 \t12:13 \t12:27 \t12:42\n" +
                    "11:46 \t12:11 \t12:36 \t12:50 \t13:05\n" +
                    "12:10 \t12:35 \t13:00 \t13:14 \t13:29\n" +
                    "12:33 \t12:58 \t13:23 \t13:37 \t13:52\n" +
                    "12:57 \t13:22 \t13:47 \t14:01 \t14:16\n" +
                    "13:20 \t13:45 \t14:10 \t14:24 \t14:39\n" +
                    "13:44 \t14:09 \t14:34 \t14:48 \t15:03\n" +
                    "14:07 \t14:32 \t14:57 \t15:11 \t15:26\n" +
                    "14:31 \t14:56 \t15:21 \t15:35 \t15:50\n" +
                    "14:54 \t15:19 \t15:44 \t15:58 \t16:13\n" +
                    "15:18 \t15:43 \t16:08 \t16:22 \t16:37\n" +
                    "15:41 \t16:06 \t16:31 \t16:45 \t17:00\n" +
                    "16:05 \t16:30 \t16:55 \t17:09 \t17:24\n" +
                    "16:28 \t16:53 \t17:18 \t17:32 \t17:47\n" +
                    "16:52 \t17:17 \t17:42 \t17:56 \t18:11\n" +
                    "17:15 \t17:40 \t18:05 \t18:19 \t18:34\n" +
                    "17:39 \t18:04 \t18:29 \t18:43 \t18:58\n" +
                    "18:02 \t18:27 \t18:52 \t19:06 \t19:21\n" +
                    "18:26 \t18:51 \t19:16 \t19:30 \t19:45\n" +
                    "18:49 \t19:14 \t19:39 \t19:53 \t20:08\n" +
                    "19:13 \t19:38 \t20:03 \t20:17 \t20:32\n" +
                    "19:36 \t20:01 \t20:56 \t20:40 \t20:55\n" +
                    "20:00 \t20:25 \t20:50 \t21:04 \t21:19\n" +
                    "20:23 \t20:48 \t21:13 \t21:27 \t21:42\n" +
                    "20:47 \t21:12 \t21:37 \t21:51 \t22:06\n" +
                    "21:10 \t21:35 \t22:00 \t22:14 \t22:29\n" +
                    "21:34 \t22:00 \t22:20 \t22:30 \t ";
    private String timeTable5E =
            "  \t  \t  \t22:30\n" +
                    "22:50 \t23:05 \t23:20 \t23:35\n" +
                    "23:50 \t00:05 \t00:20 \t00:35\n" +
                    "00:50 \t01:05 \t01:20 \t01:30";
    private String timeTable5Finde2 =
            "6:20 \t6:30 \t6:40 \t6:50 \t7:00 \t7:13 \t7:26\n" +
                    "7:40 \t7:50 \t8:00 \t8:10 \t8:20 \t8:33 \t8:46\n" +
                    "9:00 \t9:10 \t9:20 \t9:30 \t9:40 \t9:53 \t10:06\n" +
                    "10:20 \t10:30 \t10:40 \t10:50 \t11:00 \t11:13 \t11:26\n" +
                    "11:40 \t11:50 \t12:00 \t12:10 \t12:20 \t12:33 \t12:46\n" +
                    "13:00 \t13:10 \t13:20 \t13:30 \t13:40 \t13:53 \t14:06\n" +
                    "14:20 \t14:30 \t14:40 \t14:50 \t15:00 \t15:13 \t15:26\n" +
                    "15:40 \t15:50 \t16:00 \t16:10 \t16:20 \t16:33 \t16:46\n" +
                    "17:00 \t17:10 \t17:20 \t17:30 \t17:40 \t17:53 \t18:06\n" +
                    "18:20 \t18:30 \t18:40 \t18:40 \t19:00 \t19:13 \t19:26\n" +
                    "19:40 \t19:50 \t20:00 \t20:10 \t20:20 \t19:33 \t19:46\n" +
                    "21:00 \t21:10 \t21:20 \t21:30 \t21:40 \t20:47 \t21:55\n" +
                    "22:10 \t  \t  \t  \t  \t  \t ";
    private String timeTable5Finde1 =
            "  \t  \t  \t  \t6:20 \t6:33 \t6:46\n" +
                    "7:00 \t7:10 \t7:20 \t  \t7:40 \t7:53 \t8:06\n" +
                    "8:20 \t8:30 \t8:40 \t  \t9:00 \t9:13 \t9:26\n" +
                    "9:40 \t9:50 \t10:00 \t  \t10:20 \t10:33 \t10:46\n" +
                    "11:00 \t11:10 \t11:20 \t  \t11:40 \t11:53 \t12:06\n" +
                    "12:20 \t12:30 \t12:40 \t  \t13:00 \t13:13 \t13:26\n" +
                    "13:40 \t13:50 \t14:00 \t  \t14:20 \t14:33 \t14:46\n" +
                    "15:00 \t15:10 \t15:20 \t  \t15:40 \t15:53 \t16:06\n" +
                    "16:20 \t16:30 \t16:40 \t  \t17:00 \t17:13 \t17:26\n" +
                    "17:40 \t17:50 \t18:00 \t  \t18:20 \t18:33 \t18:46\n" +
                    "19:00 \t19:10 \t19:20 \t  \t19:40 \t19:53 \t20:06\n" +
                    "20:20 \t20:30 \t20:40 \t  \t21:00 \t21:13 \t21:26\n" +
                    "21:40 \t21:50 \t22:00 \t  \t22:20 \t22:05 \t22:30";
    private String timeTable5verano1 =
            "  \t  \t  \t  \t  \t5:25 \t5:35\n" +
                    "5:50 \t6:00 \t6:10 \t6:20 \t6:30 \t6:44 \t6:57\n" +
                    "7:10 \t7:20 \t7:30 \t7:40 \t7:50 \t8:04 \t8:28\n" +
                    "8:32 \t8:42 \t9:00 \t9:10 \t9:20 \t9:34 \t9:49\n" +
                    "10:04 \t10:14 \t10:24 \t10:34 \t10:44 \t10:58 \t11:13\n" +
                    "11:28 \t11:38 \t11:48 \t11:58 \t12:08 \t12:22 \t12:37\n" +
                    "12:52 \t13:02 \t13:12 \t13:24 \t13:32 \t13:46 \t14:01\n" +
                    "14:16 \t14:26 \t14:36 \t14:36 \t14:56 \t15:10 \t15:25\n" +
                    "15:40 \t15:50 \t16:00 \t16:10 \t16:20 \t16:34 \t16:49\n" +
                    "17:04 \t17:14 \t17:24 \t17:34 \t17:44 \t17:58 \t18:13\n" +
                    "18:28 \t18:38 \t18:48 \t18:58 \t19:08 \t19:22 \t19:37\n" +
                    "19:52 \t20:02 \t20:12 \t20:22 \t20:32 \t20:46 \t21:00\n" +
                    "21:12 \t21:22 \t21:32 \t21:42 \t21:52 \t22:02 \t22:22";
    private String timeTable5verano2 =
            "  \t  \t  \t  \t  \t6:07 \t6:15\n" +
                    "6:25 \t6:35 \t6:42 \t  \t7:02 \t7:13 \t7:30\n" +
                    "7:46 \t7:57 \t8:08 \t  \t8:26 \t8:38 \t8:53\n" +
                    "9:08 \t9:18 \t9:28 \t  \t9:48 \t10:02 \t10:17\n" +
                    "10:32 \t10:42 \t10:52 \t  \t11:12 \t11:26 \t11:41\n" +
                    "11:56 \t11:06 \t12:16 \t  \t12:36 \t12:50 \t13:05\n" +
                    "13:20 \t13:30 \t13:40 \t  \t14:00 \t14:14 \t14:29\n" +
                    "14:44 \t14:54 \t15:04 \t  \t15:24 \t15:38 \t15:53\n" +
                    "16:08 \t16:18 \t16:28 \t  \t16:48 \t17:02 \t17:17\n" +
                    "17:32 \t17:42 \t17:52 \t  \t18:12 \t18:26 \t18:41\n" +
                    "18:56 \t19:05 \t19:16 \t  \t19:36 \t19:50 \t20:05\n" +
                    "20:20 \t20:30 \t20:40 \t  \t21:00 \t21:14 \t21:28\n" +
                    "21:40 \t21:48 \t21:56 \t  \t  \t  \t ";
    private String timeTable5verano3 =
            "  \t  \t  \t  \t  \t6:35 \t6:40\n" +
                    "6:46 \t6:56 \t7:06 \t7:16 \t7:26 \t7:40 \t7:56\n" +
                    "8:12 \t8:22 \t8:32 \t8:42 \t8:52 \t9:06 \t9:21\n" +
                    "9:36 \t9:46 \t9:56 \t10:06 \t10:16 \t10:30 \t10:45\n" +
                    "11:00 \t11:10 \t11:20 \t11:30 \t11:40 \t11:54 \t12:09\n" +
                    "12:24 \t12:34 \t12:44 \t12:54 \t13:04 \t13:18 \t13:33\n" +
                    "13:48 \t13:58 \t14:08 \t14:18 \t14:28 \t14:42 \t14:57\n" +
                    "15:12 \t15:22 \t15:32 \t15:42 \t15:52 \t16:06 \t16:21\n" +
                    "16:36 \t16:46 \t16:56 \t17:06 \t17:16 \t17:30 \t17:45\n" +
                    "18:00 \t18:10 \t18:20 \t18:30 \t18:40 \t18:46 \t19:01\n" +
                    "19:24 \t19:34 \t19:44 \t19:54 \t20:04 \t20:20 \t20:35\n" +
                    "20:48 \t20:58 \t21:08 \t21:18 \t21:28 \t21:38 \t21:48";


    public String[] getTimeTable5(){
        return parserHorario(timeTable5);
    }

    public String[] getTimeTable5E(){
        return parserHorario(timeTable5E);
    }

    public String[] getTimeTable5SyD(){
        return parserHorarioVarios(parserBusStops(busStops5Finde).length, parserHorario(timeTable5Finde1), parserHorario(timeTable5Finde2));
    }

    public String[] getTimeTable5Verano(){
        return parserHorarioVarios(parserBusStops(busStops5Finde).length, parserHorario(timeTable5verano1), parserHorario(timeTable5verano2), parserHorario(timeTable5verano3));
    }

    public String[] getBusStops5SyD(){
        return parserBusStops(busStops5Finde);
    }

    public String[] getBusStops5(){
        return parserBusStops(busStops5);
    }

    public String[] getBusStops5E(){
        return parserBusStops(busStops5E);
    }

    /**
     * linea 6
     *
     *
     *
     */

    private String busStops6 ="EPEC \tACORD \tUNRC \tACORD \tPLAZA \tH.CENT. \tI.P.V.";
    private String busStops6E ="Plaza \tUNRC \tPlaza \tH. Central";

    private String timeTable6 =
            "  \t  \t  \t  \t  \t5:32 \t5:37\n" +
                    "5:47 \t5:59 \t6:05 \t6:12 \t6:20 \t6:35 \t6:40\n" +
                    "6:53 \t7:05 \t7:30 \t7:39 \t7:53 \t8:07 \t8:15\n" +
                    "8:30 \t8:42 \t8:50 \t8:58 \t9:10 \t9:27 \t9:35\n" +
                    "9:50 \t10:02 \t10:10 \t10:18 \t10:30 \t10:47 \t10:55\n" +
                    "11:10 \t11:22 \t11:30 \t11:38 \t11:50 \t12:07 \t12:15\n" +
                    "12:30 \t12:42 \t12:50 \t12:58 \t13:10 \t13:25 \t13:30\n" +
                    "13:45 \t13:57 \t14:05 \t14:13 \t14:23 \t14:37 \t14:42\n" +
                    "14:55 \t15:07 \t15:30 \t15:38 \t15:50 \t16:07 \t16:12\n" +
                    "16:25 \t16:42 \t16:50 \t16:58 \t17:10 \t17:27 \t17:35\n" +
                    "17:50 \t18:02 \t18:10 \t18:18 \t18:30 \t18:47 \t18:55\n" +
                    "19:10 \t19:22 \t19:30 \t19:38 \t19:50 \t20:07 \t20:15\n" +
                    "20:30 \t20:42 \t20:50 \t20:58 \t21:10 \t21:25 \t21:30\n" +
                    "21:45 \t21:55 \t22:05 \t21:12 \t22:20 \t22:30 \t22:35 ";
    private String timeTable6E =
            "  \t  \t  \t22:10\n" +
                    "22:25 \t22:45 \t23:00 \t23:10\n" +
                    "23:25 \t23:45 \t00:00 \t00:10\n" +
                    "00:25 \t00:45 \t01:00 \t01:10";


    public String[] getTimeTable6(){
        return parserHorario(timeTable6);
    }

    public String[] getTimeTable6E(){
        return parserHorario(timeTable6E);
    }



    public String[] getBusStops6(){
        return parserBusStops(busStops6);
    }

    public String[] getBusStops6E(){
        return parserBusStops(busStops6E);
    }

    /**
     *
     *
     * LINEA 7!!!
     */



    private String busStops7 ="CINEMA \tPILET. \tIPV \tPILET. \tPLAZA \tCOMUN \tTIRO FED.";

    private String timeTable7 =
            "05:25 \t05:35 \t  \t  \t05:45 \t05:52 \t06:05\n" +
                    "  \t  \t  \t  \t  \t05:30 \t05:40\n" +
                    "05:49 \t05:55 \t06:00 \t06:05 \t06:17 \t06:30 \t06:43\n" +
                    "06:20 \t06:30 \t  \t  \t06:50 \t07:00 \t07:11\n" +
                    "06:56 \t07:05 \t07:10 \t07:27 \t07:37 \t07:50 \t08:02\n" +
                    "07:22 \t07:27 \t07:35 \t07:43 \t08:03 \t08:15 \t08:30\n" +
                    "08:16 \t08:38 \t08:43 \t08:52 \t09:03 \t09:16 \t09:29\n" +
                    "09:42 \t10:03 \t10:08 \t10:13 \t10:33 \t10:46 \t10:59\n" +
                    "11:12 \t11:33 \t11:38 \t11:43 \t12:03 \t12:16 \t12.29\n" +
                    "11:55 \t12:05 \t12:17 \t12:23 \t12:45 \t13:01 \t13:14\n" +
                    "12:42 \t13:03 \t13:12 \t13:17 \t13:38 \t13:46 \t13:59\n" +
                    "13:27 \t13:48 \t13:53 \t  \t  \t  \t \n" +
                    "14:12 \t14:33 \t14:38 \t14:43 \t15:03 \t15:16 \t15:29\n" +
                    "15:42 \t16:03 \t16:08 \t16:13 \t16:33 \t16:46 \t16:59\n" +
                    "17:12 \t17:33 \t17:38 \t17:43 \t18:03 \t18:16 \t18:29\n" +
                    "  \t  \t18:17 \t18:23 \t18:45 \t19:00 \t19:18\n" +
                    "18:42 \t19:03 \t19:08 \t19:13 \t19:33 \t19:46 \t19:59\n" +
                    "19:27 \t19:37 \t19:47 \t19:53 \t20:15 \t20:30 \t20:45\n" +
                    "20:12 \t20:33 \t20:38 \t20:43 \t21:03 \t21:16 \t21:29\n" +
                    "21:36 \t21:50 \t  \t  \t  \t  \t ";
    private String timeTable7Finde =
            "  \t  \t  \t  \t  \t05:30 \t05:40\n" +
                    "05:49 \t05:55 \t06:00 \t06:05 \t06:17 \t06:30 \t06:43\n" +
                    "06:56 \t07:05 \t07:10 \t07:27 \t07:37 \t07:50 \t08:02\n" +
                    "08:16 \t08:38 \t08:43 \t08:52 \t09:03 \t09:16 \t09:29\n" +
                    "09:42 \t10:03 \t10:08 \t10:13 \t10:33 \t10:46 \t10:59\n" +
                    "11:12 \t11:33 \t11:38 \t11:43 \t12:03 \t12:16 \t12:29\n" +
                    "12:42 \t13:03 \t13:12 \t13:17 \t13:38 \t13:46 \t13:59\n" +
                    "14:12 \t14:33 \t14:38 \t14:43 \t15:03 \t15:16 \t15:29\n" +
                    "15:42 \t16:03 \t16:08 \t16:13 \t16:33 \t16:46 \t16:59\n" +
                    "17:12 \t17:33 \t17:38 \t17:43 \t18:03 \t18:16 \t18:29\n" +
                    "18:42 \t19:03 \t19:08 \t19:13 \t19:33 \t19:46 \t19:59\n" +
                    "20:12 \t20:33 \t20:38 \t20:43 \t21:03 \t21:16 \t21:29\n" +
                    "21:36 \t21:50 \t  \t  \t  \t  \t ";


    public String[] getTimeTable7(){
        return parserHorario(timeTable7);
    }

    public String[] getTimeTable7SyD() {
        return parserHorario(timeTable7Finde);

    }


    public String[] getBusStops7(){
        return parserBusStops(busStops7);
    }

    /**
     *
     *
     *
     * 8 verde
     *
     *
     */

    private String busStops8verde ="PLAZA \tUNRC \tPLAZA \tV. DALCAR \tANTENA \tGOLF";

    private String timeTable8verde =
            "  \t  \t  \t06:22 \t  \t06:29\n" +
                    "06:39 \t07:04 \t07:28 \t07:45 \tS.E. 07:53 \t08:00\n" +
                    "06:10 \t06:26 \t06:45 \t06:57 \t07:05 \t07:10\n" +
                    "07:23 \t07:48 \t08:12 \t08:30 \t08:38 \t08:43\n" +
                    "08:59 \t09:26 \t09:54 \t10:11 \t  \t10:21\n" +
                    "08:10 \tP. 08:37 \t09:05 \t09:20 \t09:29 \t09:34\n" +
                    "09:48 \t10:15 \t10:43 \t11:00 \t  \t11:10\n" +
                    "10:37 \tP. 11:04 \t11:32 \t11:49 \t  \t11:59\n" +
                    "11:26 \t11:53 \t12:21 \t12:36 \t12:45 \t12:50\n" +
                    "12:15 \tP. 12:42 \t13:10 \t13:25 \t13:32 \t13:37\n" +
                    "13:04 \t13:31 \t13:59 \t14:14 \t14:23 \t14:28\n" +
                    "13:53 \tP. 14:20 \t14:48 \t15:05 \t  \t15:15\n" +
                    "14:42 \t15:09 \t15:37 \t15:54 \t  \t16:04\n" +
                    "15:31 \t15:58 \t16:26 \t16:43 \t  \t16:53\n" +
                    "16:20 \t16:47 \t17:15 \t17:32 \t  \t17:42\n" +
                    "17:09 \t17:36 \t18:04 \t18:21 \t18:28 \t18:33\n" +
                    "17:58 \tP. 18:25 \t18:53 \t19:10 \t  \t19:20\n" +
                    "18:47 \t19:14 \t19:42 \t20:00 \t  \t20:09\n" +
                    "19:36 \t20:03 \t20:31 \t20:48 \t  \t20:58\n" +
                    "20:25 \t20:52 \t21:20 \t21:37 \t  \t21:47\n" +
                    "21:14 \t21:40 \t22:05 \t22:15 \t  \t \n" +
                    "22:01 \t22:18 \t  \t  \t  \t ";
    private String timeTable8verdeFinde =
            "  \t  \t06:25 \t06:35 \t  \t06:45\n" +
                    "06:55 \tP. 07:26 \t07:46 \t08:00 \t  \t08:10\n" +
                    "08:30 \tP. 08:53 \t09:16 \t09:30 \t09:39 \t09:44\n" +
                    "10:00 \tP. 10:23 \t10:46 \t11:00 \t11:09 \t11:14\n" +
                    "11:30 \t11:53 \t12:16 \t13:30 \t12:39 \t12:44\n" +
                    "13:00 \t13:23 \t13:46 \t14:00 \t  \t14:14\n" +
                    "14:30 \tP. 14:53 \t15:16 \t15:30 \t15:39 \t15:44\n" +
                    "16:00 \t16:23 \t16:46 \t17:00 \t17:09 \t17:14\n" +
                    "17:30 \t17:53 \t18:16 \t18:30 \t  \t18:44\n" +
                    "19:00 \tP. 19:23 \t19:46 \t20:00 \t20:09 \t20:14\n" +
                    "20:30 \t20:53 \t21:16 \t21:30 \t  \t21:44";


    public String[] getTimeTable8verde(){
        return parserHorario(timeTable8verde);
    }

    public String[] getTimeTable8verdeSyD() {
        return parserHorario(timeTable8verdeFinde);

    }


    public String[] getBusStops8verde(){
        return parserBusStops(busStops8verde);
    }


    /**
     *
     * linea 8 rojo
     */

    private String busStops8rojo ="PLAZA \tUNRC \tPLAZA \tGOLF \tANTENA \tV. DALCAR";

    private String timeTable8rojo =
            "  \t  \t05:55 \t06:07 \t  \t06:10\n" +
                    "  \t  \t06:23 \t06:37 \t  \t06:45\n" +
                    "06:17 \t06:42 \t07:06 \t07:18 \t07:23 \t07:31\n" +
                    "06:57 \tP. 07:26 \t07:50 \t08:04 \t  \t08:14\n" +
                    "07:47 \t08:14 \t08:42 \t08:58 \t  \t09:08\n" +
                    "08:34 \t09:01 \t09:29 \t09:45 \t  \t09:55\n" +
                    "09:23 \tP. 09:50 \t10:18 \t10:30 \t10:36 \t10:46\n" +
                    "10:12 \t10:39 \t11:07 \t11:19 \t11:24 \t11:33\n" +
                    "11:01 \t11:28 \t11:56 \t12:12 \t  \t12:22\n" +
                    "11:50 \t12:17 \t12:45 \t13:01 \t  \t13:11\n" +
                    "12:39 \t13:06 \t13:34 \t13:50 \t  \t14:00\n" +
                    "13:28 \t13:55 \t14:23 \t14:39 \t  \t14:49\n" +
                    "14:17 \t14:44 \t15:12 \t15:26 \t15:31 \t15:40\n" +
                    "15:06 \tP. 15:33 \t16:01 \t16:15 \t16:18 \t16:27\n" +
                    "15:55 \tP. 16:22 \t16:50 \t17:06 \t  \t17:16\n" +
                    "16:44 \t17:11 \t17:39 \t17:55 \t  \t18:05\n" +
                    "17:33 \t18:00 \t18:28 \t18:44 \t  \t18:54\n" +
                    "18:22 \t18:49 \t19:17 \t19:19 \t19:34 \t19:43\n" +
                    "19:11 \tP. 19:38 \t20:06 \t20:09 \t20:24 \t20:34\n" +
                    "20:00 \tP. 20:27 \t20:55 \t21:11 \t  \t21:21\n" +
                    "20:49 \tP. 21:14 \t21:38 \t21:50 \t  \t22:00\n" +
                    "21:38 \t22:04 \t22:20 \t22:32 \t  \t ";
    private String timeTable8rojoSabado =
            "06:10 \t06:25 \t06:45 \t06:57 \t07:01 \t07:10\n" +
                    "06:37 \t06:57 \t07:17 \t07:31 \t07:36 \t07:44\n" +
                    "07:30 \t07:53 \t08:16 \t08:30 \t08:35 \t08:44\n" +
                    "08:00 \t08:23 \t08:46 \t09:00 \t09:05 \t09:14\n" +
                    "09:00 \t09:23 \t09:46 \t10:00 \t10:05 \t10:14\n" +
                    "09:30 \t09:53 \t10:16 \t10:30 \t  \t10:44\n" +
                    "10:30 \t10:53 \t11:16 \t11:30 \t  \t11:44\n" +
                    "11:00 \tP. 11:23 \t11:46 \t12:00 \t  \t12:14\n" +
                    "12:00 \t12:23 \t12:46 \t13:00 \t13:05 \t13:14\n" +
                    "12:30 \tP. 12:53 \t13:16 \t13:30 \t  \t13:44\n" +
                    "13:30 \t13:53 \t14:16 \t14:30 \t14:35 \t14:44\n" +
                    "14:00 \t14:23 \t14:46 \t15:00 \t  \t15:14\n" +
                    "15:00 \t15:23 \t15:46 \t16:00 \t  \t16:14\n" +
                    "15:30 \tP. 15:53 \t16:16 \t16:30 \t16:36 \t16:44\n" +
                    "16:30 \t16:53 \t17:16 \t17:30 \t  \t17:44\n" +
                    "17:00 \t17:23 \t17:46 \t18:00 \t18:05 \t18:14\n" +
                    "18:00 \tP. 18:23 \t18:46 \t19:00 \t  \t19:14\n" +
                    "18:30 \t18:53 \t19:16 \t19:30 \t19:35 \t19:44\n" +
                    "19:30 \tP. 19:53 \t20:16 \t20:30 \t  \t20:44\n" +
                    "20:00 \t20:23 \t20:46 \t21:00 \t  \t21:14\n" +
                    "21:00 \tP. 21:23 \t21:46 \t  \t  \t \n" +
                    "21:30 \t21:53 \t  \t  \t  \t ";

    private String timeTable8rojoDomingo =
            "  \t  \t06:20 \t06:35 \t  \t06:45\n" +
                    "06:20 \t06:40 \t07:00 \t07:13 \t07:18 \t07:26\n" +
                    "07:00 \tP. 07:20 \t07:40 \t07:55 \t  \t08:05\n" +
                    "07:40 \t08:02 \t08:25 \t08:39 \t08:44 \t08:54\n" +
                    "08:25 \tP. 08:47 \t09:10 \t09:24 \t  \t09:39\n" +
                    "09:10 \t09:32 \t09:55 \t10:09 \t10:14 \t10:24\n" +
                    "09:55 \tP. 10:17 \t10:40 \t10:54 \t  \t11:09\n" +
                    "10:40 \t11:02 \t11:25 \t11:39 \t11:44 \t11:54\n" +
                    "11:25 \tP. 11:47 \t12:10 \t12:24 \t  \t12:39\n" +
                    "12:10 \t12:32 \t12:55 \t13:09 \t13:14 \t13:24\n" +
                    "12:55 \tP. 13:17 \t13:40 \t13:54 \t  \t14:09\n" +
                    "13:40 \t14:02 \t14:25 \t14:39 \t14:44 \t14:54\n" +
                    "14:25 \tP. 14:47 \t15:10 \t15:24 \t  \t15:39\n" +
                    "15:10 \t15:32 \t15:55 \t16:09 \t16:14 \t16:24\n" +
                    "15:55 \tP. 16:17 \t16:40 \t16:54 \t  \t17:09\n" +
                    "16:40 \t17:02 \t17:25 \t17:39 \t17:14 \t17:54\n" +
                    "17:25 \tP. 17:47 \t18:10 \t18:24 \t  \t18:39\n" +
                    "18:10 \t18:32 \t18:55 \t19:09 \t19:14 \t19:24\n" +
                    "18:55 \tP. 19:17 \t19:40 \t19:54 \t  \t20:09\n" +
                    "19:40 \t20:02 \t20:25 \t20:39 \t20:44 \t20:54\n" +
                    "20:25 \tP. 20:47 \t21:10 \t21:24 \t  \t21:39\n" +
                    "21:10 \t21:32 \t21:55 \t22:09 \t  \t22:24\n" +
                    "21:55 \t22:17 \t  \t  \t  \t ";


    public String[] getTimeTable8rojo(){
        return parserHorario(timeTable8rojo);
    }

    public String[] getTimeTable8rojoS() {
        return parserHorario(timeTable8rojoSabado);

    }

    public String[] getTimeTable8rojoD() {
        return parserHorario(timeTable8rojoDomingo);

    }

    public String[] getBusStops8rojo(){
        return parserBusStops(busStops8rojo);
    }

    /*+
    linea 9 verede
     */



    private String busStops9verde ="U.N.R.C \tM.ABAS \tPLAZA \tPEYRAN \tIPV.ALV \tE.P.E.C \tIPV.B.N";

    private String timeTable9verde =
            "  \t  \t  \t  \t  \t06:00 \t06:15\n" +
                    "  \t  \t05:57 \t06:18 \t06:29 \t06:39 \t07:00\n" +
                    "06:27 \t06:42 \t06:58 \t07:19 \t07:29 \t07:40 \t08:01\n" +
                    "07:20 \t07:39 \t07:56 \t08:18 \t08:29 \t08:39 \t08:58\n" +
                    "08:20 \t08:40 \t08:56 \t09:18 \t09:29 \t09:40 \t09:59\n" +
                    "09:20 \t09:40 \t09:56 \t10:18 \t10:29 \t10:40 \t10:59\n" +
                    "10:20 \t10:40 \t10:56 \t11:18 \t11:29 \t11:40 \t11:59\n" +
                    "11:20 \t11:40 \t11:56 \t12:18 \t12:29 \t12:40 \t12:59\n" +
                    "12:20 \t12:40 \t12:56 \t13:18 \t13:29 \t13:40 \t13:59 \n" +
                    "13:20 \t13:40 \t13:56  \t14:18 \t14:29 \t14:40 \t14:59\n" +
                    "14:20 \t14:40 \t14:56 \t15:18 \t15:29 \t15:40 \t15:59\n" +
                    "15:20 \t15:40 \t15:56 \t16:18 \t16:29 \t16:40 \t16:59\n" +
                    "16:20 \t16:40 \t16:56 \t17:18 \t17:29 \t17:40 \t17:59\n" +
                    "17:20 \t17:40 \t17:56 \t18:18 \t18:29 \t18:40 \t18:59\n" +
                    "18:20 \t18:40 \t18:56 \t19:18 \t19:29 \t19:40 \t19:59\n" +
                    "19:20 \t19:40 \t19:56 \t20:18 \t20:28 \t20:36 \t20:50\n" +
                    "20:20 \t20:40 \t20:56 \t21:16 \t21:24 \t21:30 \t21:45\n" +
                    "21:05 \t21:20 \t21:35 \t21:50 \t22:00 \t  \t \n" +
                    "22:00 \t  \t  \t  \t  \t  \t ";



    public String[] getTimeTable9verde(){
        return parserHorario(timeTable9verde);
    }


    public String[] getBusStops9verde(){
        return parserBusStops(busStops9verde);
    }


    /**
     * linea 9 rojo
     */

    private String busStops9rojo ="U.N.R.C \tM.ABAS \tPLAZA \tIPV.ALV \tPEYRAN \tE.P.E.C \tIPV.B.N";

    private String timeTable9rojo =
            "  \t  \t  \t05:55 \t06:05 \t06:25 \t06:39\n" +
                    "06:00 \t06:13 \t06:28 \t06:40 \t06:50 \t07:11 \t07:28\n" +
                    "06:50 \t07:07 \t07:20 \t07:29 \t07:39 \t08:02 \t08:21\n" +
                    "07:50 \t08:10 \t08:26 \t08:37 \t08:48 \t09:10 \t09:29\n" +
                    "08:50 \t09:10 \t09:26 \t09:37 \t09:48 \t10:10 \t10:29\n" +
                    "09:50 \t10:10 \t10:26 \t10:37 \t10:48 \t11:10 \t11:29\n" +
                    "10:50 \t11:10 \t11:26 \t11:37 \t11:48 \t12:10 \t12:29\n" +
                    "11:50 \t12:10 \t12:26 \t12:37 \t12:48 \t13:10 \t13:29\n" +
                    "12:50 \t13:10 \t13:26 \t13:37 \t13:48 \t14:10  \t14:29\n" +
                    "13:50 \t14:10 \t14:26  \t14:37 \t14:48 \t15:10 \t15:29\n" +
                    "14:50 \t15:10 \t15:26 \t15:37 \t15:48 \t16:10 \t16:29\n" +
                    "15:50 \t16:10 \t16:26 \t16:37 \t16:48 \t17:10 \t17:29\n" +
                    "16:50 \t17:10 \t17:26 \t17:37 \t17:48 \t18:10 \t18:29\n" +
                    "17:50 \t18:10 \t18:26 \t18:37 \t18:48 \t19:10 \t19:29\n" +
                    "18:50 \t19:10 \t19:26 \t19:37 \t19:48 \t20:10 \t20:29\n" +
                    "19:50 \t20:10 \t20:26 \t20:35 \t20:43 \t21:00 \t21:15\n" +
                    "20:50 \t21:10 \t21:26 \t21:34 \t21:43 \t22:00 \t22:15\n" +
                    "21:30 \t21:45 \t22:00 \t22:08 \t22:15 \t  \t \n" +
                    "22:30 \t  \t  \t  \t  \t  \t ";



    public String[] getTimeTable9rojo(){
        return parserHorario(timeTable9rojo);
    }


    public String[] getBusStops9rojo(){
        return parserBusStops(busStops9rojo);
    }

    /**
     * linea 10
     */


    private String busStops10 ="Plaza  \tIPV F. \tYapeyú \tIPV F. \tPlaza  \tComunic \t400 Viv";

    private String timeTable10 =
            "  \t  \t  \t  \t  \t06:10 \t06:20\n" +
                    "06:30 \t06:42 \t06:50 \t07:02 \t07:15 \t07:25 \t07:35\n" +
                    "07:45 \t07:58 \t08:09 \t08:23 \t08:35 \t08:45 \t08:55\n" +
                    "09:05 \t09:20 \t09:31 \t09:45 \t10:00 \t10:10 \t10:20\n" +
                    "10:35 \t10:50 \t11:01 \t11:15 \t11:30 \t11:40 \t11:50\n" +
                    "12:05 \t12:20 \t13:31 \t12:45 \t13:00 \t13:10 \t13:20\n" +
                    "13:35 \t13:50 \t14:01 \t14:15 \t14:30 \t14:40 \t14:50\n" +
                    "15:05 \t15:20 \t15:31 \t15:45 \t16:00 \t16:10 \t16:20\n" +
                    "16:35 \t16:50 \t17:01 \t17:15 \t17:30 \t17:40 \t17:50\n" +
                    "18:05 \t18:20 \t18:31 \t18:45 \t19:00 \t19:10 \t19:20\n" +
                    "19:35 \t19:50 \t20:01 \t20:15 \t20:30 \t20:40 \t20:50\n" +
                    "21:05 \t21:20 \t21:31 \t21:45 \t21:55 \t22:05 \t22:15";



    public String[] getTimeTable10(){
        return parserHorario(timeTable10);
    }


    public String[] getBusStops10(){
        return parserBusStops(busStops10);
    }


    /**
     *
     * linea 11
     */

    private String busStops11 ="Plaza  \tIPV.Feni \tYapeyu \tIPV.Feni \tPlaza \t400 Vivi \tComun";

    private String timeTable11 =
            "  \t  \t06:10 \t06:20 \t06:30 \t06:45 \t06:50\n" +
                    "07:00 \t07:11 \t07:20 \t07:33 \t07:44 \t08:02 \t08:10\n" +
                    "08:20 \t08:35 \t08:46 \t09:00 \t09:15 \t09:32 \t09:38\n" +
                    "09:50 \t10:05 \t10:16 \t10:30 \t10:45 \t11:02 \t11:08\n" +
                    "11:20 \t11:35 \t11:46 \t12:00 \t12:15 \t12:32 \t12:38\n" +
                    "12:50 \t13:05 \t13:16 \t13:30 \t13:45 \t14:02 \t14:08\n" +
                    "14:20 \t14:35 \t14:46 \t15:00 \t15:15 \t15:32 \t15:38\n" +
                    "15:50 \t16:05 \t16:16 \t16:30 \t16:45 \t17:02 \t17:08\n" +
                    "17:20 \t17:35 \t17:46 \t18:00 \t18:15 \t18:32 \t18:38\n" +
                    "18:50 \t19:05 \t19:16 \t19:30 \t19:45 \t20:02 \t20:08\n" +
                    "20:20 \t20:35 \t20:46 \t21:00 \t21:15 \t21:32 \t21:38\n" +
                    "21:50 \t22:05 \t22:16 \t  \t  \t  \t ";



    public String[] getTimeTable11(){
        return parserHorario(timeTable11);
    }


    public String[] getBusStops11(){
        return parserBusStops(busStops11);
    }

    /**
     * linea 12
     */


    private String busStops12 ="EPEC \tUNRC \tPLAZA \tH. CENTRAL";

    private String timeTable12 =
            "  \t  \t  \t06:05\n" +
                    "06:20 \t06:35 \t06:55 \t07:20\n" +
                    "06:55 \t07:20 \t07:40 \t07:55\n" +
                    "07:40 \t08:20 \t08:40 \t09:00\n" +
                    "08:15 \t09:05 \t09:30 \t09:45\n" +
                    "09:20 \t09:50 \t10:15 \t10:30\n" +
                    "10:05 \t10:35 \t11:00 \t11:15\n" +
                    "10:50 \t11:20 \t11:45 \t12:00\n" +
                    "11:35 \t12:05 \t12:30 \t12:45\n" +
                    "12:20 \t12:50 \t13:15 \t13:30\n" +
                    "13:05 \t13:35 \t14:00 \t14:15\n" +
                    "13:50 \t14:20 \t14:45 \t15:00\n" +
                    "14:35 \t15:05 \t15:30 \t15:45\n" +
                    "15:20 \t15:50 \t16:15 \t16:30\n" +
                    "16:05 \t16:35 \t17:00 \t17:15\n" +
                    "16:50 \t17:20 \t17:45 \t18:00\n" +
                    "17:35 \t18:05 \t18:30 \t18:45\n" +
                    "18:20 \t18:50 \t19:15 \t19:30\n" +
                    "19:05 \t19:35 \t20:00 \t20:15\n" +
                    "19:50 \t20:20 \t20:45 \t21:00\n" +
                    "20:35 \t21:00 \t21:20 \t21:35\n" +
                    "21:15 \t21:45 \t22:05 \t22:20";

    private String timeTable12Sabado =
            "  \t  \t  \t06:05\n" +
                    "06:20 \t06:35 \t06:55 \t07:20\n" +
                    "06:55 \t07:20 \t07:40 \t07:55\n" +
                    "07:40 \t08:20 \t08:40 \t09:00\n" +
                    "08:15 \t09:05 \t09:30 \t09:45\n" +
                    "09:20 \t09:50 \t10:15 \t10:30\n" +
                    "10:05 \t10:35 \t11:00 \t11:15\n" +
                    "10:50 \t11:20 \t11:45 \t12:00\n" +
                    "11:35 \t12:05 \t12:30 \t12:45\n" +
                    "12:20 \t12:50 \t13:15 \t13:30\n" +
                    "13:05 \t13:35 \t14:00 \t14:15\n" +
                    "13:50 \t14:20 \t14:45 \t15:00\n" +
                    "15:20 \t15:50 \t16:15 \t16:30\n" +
                    "16:50 \t17:20 \t17:45 \t18:00\n" +
                    "18:20 \t18:50 \t19:15 \t19:30\n" +
                    "19:50 \t20:20 \t20:45 \t21:00\n" +
                    "21:15 \t21:45 \t22:05 \t22:20";

    private String timeTable12Domingo =
            "  \t  \t  \t06:05\n" +
                    "06:20 \t06:35 \t06:55 \t07:20\n" +
                    "07:40 \t08:20 \t08:40 \t09:00\n" +
                    "09:20 \t09:50 \t10:15 \t10:30\n" +
                    "10:50 \t11:20 \t11:45 \t12:00\n" +
                    "12:20 \t12:50 \t13:15 \t13:30\n" +
                    "15:20 \t15:50 \t16:15 \t16:30\n" +
                    "16:50 \t17:20 \t17:45 \t18:00\n" +
                    "18:20 \t18:50 \t19:15 \t19:30\n" +
                    "19:50 \t20:20 \t20:45 \t21:00\n" +
                    "21:15 \t21:45 \t22:05 \t22:20";


    public String[] getTimeTable12(){
        return parserHorario(timeTable12);
    }

    public String[] getTimeTable12Sabado(){
        return parserHorario(timeTable12Sabado);
    }
    public String[] getTimeTable12Domingo(){
        return parserHorario(timeTable12Domingo);
    }

    public String[] getBusStops12(){
        return parserBusStops(busStops12);
    }

    /**
     * linea 13
     */


    private String busStops13 ="PLAZA  \tUNRC \tPLAZA \tCOMUNIC \tCEMENT";
    private String busStops13Finde ="PLAZA  \tACOR \tUNRC. \tACOR \tPLAZA  \tCOMUN \tCEMEN";

    private String timeTable13 =
            "  \t  \t  \t06:10 \t06:20\n" +
                    "  \t  \t06:20 \t06:30 \t06:43\n" +
                    "06:00 \t06:20 \t06:40 \t06:50 \t07:02\n" +
                    "06:20 \t06:40 \t07:00 \t07:12 \t07:26\n" +
                    "06:31 \t06:55 \t07:20 \t07:33 \t07:50\n" +
                    "07:00 \t07:20 \t07:40 \t07:53 \t08:10\n" +
                    "07:16 \t07:41 \t08:06 \t08:19 \t08:36\n" +
                    "07:39 \t08:04 \t08:29 \t08:42 \t08:59\n" +
                    "08:03 \t08:28 \t08:53 \t09:06 \t09:23\n" +
                    "08:26 \t08:51 \t09:16 \t09:29 \t09:46\n" +
                    "08:50 \t09:15 \t09:40 \t09:53 \t10:10\n" +
                    "09:13 \t09:38 \t10:03 \t10:16 \t10:33\n" +
                    "09:37 \t10:02 \t10:27 \t10:40 \t10:57\n" +
                    "10:00 \t10:25 \t10:50 \t11:03 \t11:20\n" +
                    "10:24 \t10:49 \t11:14 \t11:27 \t11:44\n" +
                    "10:47 \t11:12 \t11:37 \t11:50 \t12:07\n" +
                    "11:11 \t11:36 \t12:01 \t12:14 \t12:31\n" +
                    "11:34 \t11:59 \t12:24 \t12:37 \t12:54\n" +
                    "11:58 \t12:23 \t12:48 \t13:01 \t13:18\n" +
                    "12:21 \t12:46 \t13:11 \t13:24 \t13:41\n" +
                    "12:45 \t13:10 \t13:35 \t13:48 \t14:05\n" +
                    "13:08 \t13:33 \t13:58 \t14:11 \t14:28\n" +
                    "13:32 \t13:57 \t14:22 \t14:35 \t14:52\n" +
                    "13:55 \t14:20 \t14:45 \t14:58 \t15:15\n" +
                    "14:19 \t14:44 \t15:09 \t15:22 \t15:39\n" +
                    "14:42 \t15:07 \t15:32 \t15:45 \t16:02\n" +
                    "15:06 \t15:31 \t15:56 \t16:09 \t16:26\n" +
                    "15:29 \t15:54 \t16:19 \t16:32 \t16:49\n" +
                    "15:53 \t16:18 \t16:43 \t16:56 \t17:13\n" +
                    "16:16 \t16:41 \t17:06 \t17:19 \t17:36\n" +
                    "16:40 \t17:05 \t17:30 \t17:43 \t18:00\n" +
                    "17:03 \t17:28 \t17:53 \t18:06 \t18:23\n" +
                    "17:27 \t17:52 \t18:17 \t18:30 \t18:47\n" +
                    "17:50 \t18:15 \t18:40 \t18:53 \t19:10\n" +
                    "18:14 \t18:39 \t19:04 \t19:17 \t19:34\n" +
                    "18:37 \t19:02 \t19:27 \t19:40 \t19:57\n" +
                    "19:01 \t19:26 \t19:51 \t20:04 \t20:21\n" +
                    "19:24 \t19:49 \t20:14 \t20:27 \t20:44\n" +
                    "19:48 \t20:13 \t20:38 \t20:51 \t21:08\n" +
                    "20:11 \t20:36 \t21:01 \t21:14 \t21:31\n" +
                    "20:35 \t21:00 \t21:25 \t21:38 \t \n" +
                    "20:58 \t21:23 \t21:48 \t22:01 \t \n" +
                    "21:22 \t  \t  \t  \t \n" +
                    "21:45 \t  \t  \t  \t ";

    private String timeTable13Finde1 =
            "  \t  \t  \t  \t  \t6:10 \t6:20\n" +
                    "6:35 \t6:48 \t   6:55 \t7:05 \t7:20 \t7:32 \t7:47\n" +
                    "8:00 \t8:10 \t8:20 \t8:30 \t8:40 \t8:52 \t8:07\n" +
                    "9:20 \t9:30 \t9:40 \t9:50 \t10:00 \t10:12 \t10:27\n" +
                    "10:40 \t10:50 \t11:00 \t11:10 \t11:20 \t11:32 \t11:47\n" +
                    "12:00 \t12:10 \t12:20 \t12:30 \t12:40 \t12:52 \t13:07\n" +
                    "13:20 \t13:30 \t13:40 \t13:50 \t14:00 \t14:12 \t14:27\n" +
                    "14:40 \t14:50 \t15:00 \t15:10 \t15:20 \t15:32 \t15:47\n" +
                    "16:00 \t16:10 \t16:20 \t16:30 \t16:40 \t16:52 \t17:07\n" +
                    "17:20 \t17:30 \t17:40 \t17:50 \t18:00 \t18:12 \t18:27\n" +
                    "18:40 \t18:40 \t19:00 \t19:10 \t19:20 \t19:32 \t19:47\n" +
                    "20:00 \t20:10 \t20:20 \t20:30 \t20:40 \t20:42 \t20:47\n" +
                    "21:20 \t21:30 \t21:40 \t21:50 \t22:00 \t22:12 \t22:48";

    private String timeTable13Finde2 =
            "6:00 \t6:10 \t6:20 \t6:30 \t6:40 \t6:52 \t7:07\n" +
                    "7:20 \t7:30 \t7:40 \t7:50 \t8:00 \t8:12 \t8:27\n" +
                    "8:40 \t8:50 \t9:00 \t9:00 \t9:20 \t9:32 \t9:47\n" +
                    "10:00 \t10:10 \t10:20 \t10:30 \t10:40 \t10:52 \t11:07\n" +
                    "11:20 \t11:30 \t11:40 \t11:50 \t12:00 \t12:12 \t12:27\n" +
                    "12:40 \t12:50 \t13:00 \t13:10 \t13:20 \t13:32 \t13:47\n" +
                    "14:00 \t14:10 \t14:20 \t14:30 \t14:40 \t14:52 \t15:07\n" +
                    "15:20 \t15:30 \t15:40 \t15:50 \t16:00 \t16:12 \t16:27\n" +
                    "16:40 \t16:50 \t17:00 \t17:10 \t17:20 \t17:32 \t17:47\n" +
                    "18:00 \t18:10 \t18:20 \t18:30 \t18:40 \t18:52 \t19:07\n" +
                    "19:20 \t19:30 \t19:40 \t19:50 \t20:00 \t20:12 \t20:27\n" +
                    "20:40 \t20:50 \t21:00 \t21:10 \t21:20 \t21:30 \t21:35";


    public String[] getTimeTable13(){
        return parserHorario(timeTable13);
    }

    public String[] getTimeTable13Finde(){
        return parserHorarioVarios(getBusStops13Finde().length, parserHorario(timeTable13Finde1), parserHorario(timeTable13Finde2));
    }


    public String[] getBusStops13(){
        return parserBusStops(busStops13);
    }

    public String[] getBusStops13Finde(){
        return parserBusStops(busStops13Finde);
    }


    /**
     * linea 14
     */

    private String busStops14Maniana ="H.CENT \tWOLM \tPLAZA \tH.S.A.PA \tCATED \tWOLM";
    private String busStops14Tarde ="PLAZA \tWALMAR \tB.CASTELLI \tB.BIMAC \tWALMAR \tMUNICIP.";



    private String timeTable14Maniana =
            "05:30 \t05:38 \t  \t05:50 \t  \t \n" +
                    "06:10 \t06:20 \t06:35 \t06:43 \t06:50 \t07:03\n" +
                    "07:20 \t07:40 \t08:00 \t  \t  \t ";

    private String timeTable14Tarde =
            "  \t  \t  \t  \t  \t08:15\n" +
                    "08:20 \t08:38 \t08:48 \t08:53 \t09:00 \t09:15\n" +
                    "09:20 \t09:38 \t09:48 \t09:53 \t10:00 \t10:15\n" +
                    "10:20 \t10:38 \t10:48 \t10:53 \t11:00 \t11:15\n" +
                    "11:20 \t11:38 \t11:48 \t11:53 \t12:00 \t12:15\n" +
                    "12:20 \t12:38 \t12:46 \t12:50 \t12:55 \t13:10\n" +
                    "13:15 \t13:31 \t13:39 \tR.13:43 \t13:50 \t14:05\n" +
                    "14:10 \t14:26 \t14:34 \t14:38 \t14:45 \t15:00\n" +
                    "15:05 \t15:21 \t15:29 \t15:33 \t15:40 \t16:00\n" +
                    "16:20 \t16:38 \t16:48 \t16:53 \t17:00 \t17:15\n" +
                    "17:20 \t17:38 \t17:48 \t17:53 \t18:00 \t18:15\n" +
                    "18:20 \t18:38 \t18:48 \t18:53 \t19:00 \t19:15\n" +
                    "19:20 \t19:38 \t19:48 \t19:53 \t20:00 \t20:15\n" +
                    "20:20 \t20:38 \t20:48 \t20:53 \t21:00 \t21:15\n" +
                    "21:20 \t21:30 \t21:40 \t  \t  \t ";


    public String[] getTimeTable14Maniana(){
        return parserHorario(timeTable14Maniana);
    }


    public String[] getTimeTable14Tarde(){
        return parserHorario(timeTable14Tarde);
    }



    public String[] getBusStops14Maniana(){
        return parserBusStops(busStops14Maniana);
    }
    public String[] getBusStops14Tarde(){
        return parserBusStops(busStops14Tarde);
    }

    /**
     * linea 15
     *
     */

    private String busStops15 ="C.SALU \tEPEC. \tPARQU \tWITTO. \tPARQU \tPLAZA";


    private String timeTable15 =
            "6:10 \t6:15 \t6:21 \t6:30 \t6:44 \t6:50\n" +
                    "6,55 \t7:00 \t7:09 \t7:30 \t7:44 \t7:50\n" +
                    "7,55 \t8:00 \t8:09 \t8:30 \t8:46 \t8:55\n" +
                    "9:00 \t9:05 \t9:14 \t9:30 \t9:46 \t9:55\n" +
                    "10:00 \t10:05 \t10:14 \t10:30 \t10:46 \t10:55\n" +
                    "11:00 \t11:05 \t11:14 \t11:35 \t11:52 \t12:00\n" +
                    "12:00 \t12:10 \t12:19 \t12:35 \t12:52 \t13:00\n" +
                    "13:05 \t13:10 \t13:19 \t13:30 \t13:46 \t13:55\n" +
                    "14:00 \t14:05 \t14:14 \t14:30 \t14:44 \t14:50\n" +
                    "14:55 \t15:00 \t15:09 \t15:30 \t15:44 \t15:50\n" +
                    "15:55 \t16:00 \t16:09 \t16:30 \t16:46 \t16:55\n" +
                    "17:00 \t17:05 \t17:14 \t17:30 \t17:46 \t17:55\n" +
                    "18:00 \t18:05 \t18:14 \t18:35 \t18:52 \t19:00\n" +
                    "19:05 \t19:10 \t19:19 \t19:35 \t19:52 \t20:00\n" +
                    "20:05 \t20:10 \t20:19 \t20:30 \t20:46 \t20:55\n" +
                    "21:00 \t21:05 \t21:14 \t21:30 \t21:43 \t21:50";



    public String[] getTimeTable15(){
        return parserHorario(timeTable15);
    }

    public String[] getBusStops15(){
        return parserBusStops(busStops15);
    }


    /**
     * linea 16
     */

    private String busStops16 ="UNRC \tI.P.V. \tALBERDI \t320 Viv.";


    private String timeTable16 =
            "  \t6:30 \t6:35 \t6:45\n" +
                    "7:00 \t7:30 \t7:35 \t7:45\n" +
                    "8:00 \t8:30 \t8:35 \t8:45\n" +
                    "9:00 \t9:30 \t9:35 \t9:45\n" +
                    "10:00 \t10:30 \t10:35 \t10:45\n" +
                    "11:00 \t11:30 \t11:35 \t11:45\n" +
                    "12:00 \t12:30 \t12:35 \t12:45\n" +
                    "13:00 \t13:30 \t13:35 \t13:45\n" +
                    "14:00 \t14:30 \t14:35 \t14:45\n" +
                    "15:00 \t15:30 \t15:35 \t15:45\n" +
                    "16:00 \t16:30 \t16:35 \t16:45\n" +
                    "17:00 \t17:30 \t17:35 \t17:45\n" +
                    "18:00 \t18:30 \t18:35 \t18:45\n" +
                    "19:00 \t19:30 \t19:35 \t19:45\n" +
                    "20:00 \t20:30 \t20:35 \t20:45\n" +
                    "21:00 \t21:30 \t21:35 \t21:45\n" +
                    "22:00 \t22:30 \t22:35 \t ";



    public String[] getTimeTable16(){
        return parserHorario(timeTable16);
    }

    public String[] getBusStops16(){
        return parserBusStops(busStops16);
    }


    private String busStops17 ="PLAZA \tUNRC \tB.UNIV \tPLAZA \tS.A.PAD";


    private String timeTable17 =
            "6:00 \t  \t6:15 \t6:25 \t6:33\n" +
                    "6:40 \t6:55 \t7:05 \t7:20 \t7:30\n" +
                    "7:40 \t8:00 \t8:15 \t8:20 \t8:30\n" +
                    "8:40 \t9:00 \t9:15 \t9:20 \t9:30\n" +
                    "9:40 \t10:00 \t10:15 \t10:20 \t10:30\n" +
                    "10:40 \t11:00 \t11:15 \t11:20 \t11:30\n" +
                    "11:40 \t12:00 \t12:15 \t12:20 \t12:30\n" +
                    "12:40 \t13:00 \t13:15 \t13:20 \t13:30\n" +
                    "13:40 \t14:05 \t14:15 \t14:20 \t14:30\n" +
                    "14:40 \t15:00 \t15:15 \t15:20 \t15:30\n" +
                    "15:40 \t16:00 \t16:15 \t16:20 \t16:30\n" +
                    "16:40 \t17:00 \t17:15 \t17:20 \t17:30\n" +
                    "17:40 \t18:00 \t18:15 \t18:20 \t18:30\n" +
                    "18:40 \t19:00 \t19:15 \t19:20 \t19:30\n" +
                    "19:40 \t20:00 \t20:15 \t20:20 \t20:30\n" +
                    "20:40 \t0:00 \t21:15 \t21:20 \t21:30";



    public String[] getTimeTable17(){
        return parserHorario(timeTable17);
    }

    public String[] getBusStops17(){
        return parserBusStops(busStops17);
    }

    /*
    linea 18
     */

    private String busStops18 ="Plaza \tH. Nuevo";


    private String timeTable181 =
            "5:50 \t6:00\n" +
                    "6:15 \t6:30\n" +
                    "6:45 \t7:00\n" +
                    "7:15 \t7:30\n" +
                    "7:45 \t8:00\n" +
                    "7:40 \t8:00\n" +
                    "8:20 \t8:40\n" +
                    "9:00 \t9:20\n" +
                    "9:40 \t10:00\n" +
                    "10:20 \t10:40\n" +
                    "11:00 \t11:20\n" +
                    "11:40 \t12:00\n" +
                    "12:20 \t12:40\n" +
                    "13:00 \t13:20\n" +
                    "13:40 \t14:00\n" +
                    "14:20 \t14:40\n" +
                    "15:00 \t15:20\n" +
                    "15:40 \t16:00\n" +
                    "16:20 \t16:40\n" +
                    "17:00 \t17:20\n" +
                    "17:40 \t18:00\n" +
                    "18:20 \t18:40\n" +
                    "19:00 \t19:20\n" +
                    "19:40 \t20:00\n" +
                    "20:15 \t20:30\n" +
                    "20:45 \t21:00\n" +
                    "21:15 \t21:30\n" +
                    "21:45 \t ";

    private String timeTable182 =
            "6:00 \t6:15\n" +
                    "6:30 \t6:45\n" +
                    "7:00 \t7:15\n" +
                    "7:30 \t7:45\n" +
                    "8:00 \t8:20\n" +
                    "8:40 \t9:00\n" +
                    "9:20 \t9:40\n" +
                    "10:00 \t10:20\n" +
                    "10:40 \t11:00\n" +
                    "11:20 \t11:40\n" +
                    "12:00 \t12:20\n" +
                    "12:40 \t13:00\n" +
                    "13:20 \t13:40\n" +
                    "14:00 \t14:20\n" +
                    "14:40 \t15:00\n" +
                    "15:20 \t15:40\n" +
                    "16:00 \t16:20\n" +
                    "16:40 \t17:00\n" +
                    "17:20 \t17:40\n" +
                    "18:00 \t18:20\n" +
                    "18:40 \t19:00\n" +
                    "19:20 \t19:40\n" +
                    "20:00 \t20:15\n" +
                    "20:30 \t20:45\n" +
                    "21:00 \t21:15\n" +
                    "21:30 \t21:45\n" +
                    "22:00 \t ";

    private String timeTable18Finde =
            "05:50 \t06:00\n" +
                    "06:15 \t06:30\n" +
                    "06:45 \t07:00\n" +
                    "07:15 \t07:30\n" +
                    "07:45 \t08:00\n" +
                    "08:15 \t08:30\n" +
                    "08:45 \t09:00\n" +
                    "09:15 \t09:30\n" +
                    "09:45 \t10:00\n" +
                    "10:15 \t10:30\n" +
                    "10:45 \t11:00\n" +
                    "11:15 \t11:30\n" +
                    "11:45 \t12:00\n" +
                    "12:15 \t12:30\n" +
                    "12:45 \t13:00\n" +
                    "13:15 \t13:30\n" +
                    "13:45 \t14:00\n" +
                    "14:15 \t14:30\n" +
                    "14:45 \t15:00\n" +
                    "15:15 \t15:30\n" +
                    "15:45 \t16:00\n" +
                    "16:15 \t16:30\n" +
                    "16:45 \t17:00\n" +
                    "17:15 \t17:30\n" +
                    "17:45 \t18:00\n" +
                    "18:15 \t18:30\n" +
                    "18:45 \t19:00\n" +
                    "19:15 \t19:30\n" +
                    "19:45 \t20:00\n" +
                    "20:15 \t20:30\n" +
                    "20:45 \t21:00\n" +
                    "21:15 \t21:30\n" +
                    "21:45 \t22:05\n" +
                    "22:12 \t22:20";

    private String timeTable18Especial =
            "21:47 \t22:10\n" +
                    "22:17 \t22:29\n" +
                    "22:41 \t22:53\n" +
                    "23:05 \t23:17\n" +
                    "23:29 \t23:41\n" +
                    "23:53 \t00:05\n" +
                    "00:17 \t00:29\n" +
                    "00:41 \t ";



    public String[] getTimeTable18(){
        return parserHorarioVarios(getBusStops18().length, parserHorario(timeTable181), parserHorario(timeTable182));
    }

    public String[] getBusStops18(){
        return parserBusStops(busStops18);
    }

    public String[] getTimeTable18Finde(){
        return parserHorario(timeTable18Finde);
    }

    public String[] getTimeTable18Especial(){
        return parserHorario(timeTable18Especial);
    }

    /**
     * linea A
     *
     *
     */

    private String busStopsA ="Barrio Univ. \tUNRC \tLas Higueras \tUNRC";


    private String timeTableA =
            "06:00 \t06:05 \t06:15 \t06:25\n" +
                    "06:30 \t06:35 \t06:45 \t06:55\n" +
                    "07:00 \t07:05 \t07:15 \t07:25\n" +
                    "07:30 \t07:35 \t07:45 \t07:55\n" +
                    "00:00 \t00:05 \t00:15 \t00:25\n" +
                    "08:04 \t08:09 \t08:19 \t08:29\n" +
                    "09:20 \t09:25 \t09:35 \t09:45\n" +
                    "10:00 \t10:05 \t10:15 \t10:25\n" +
                    "10:04 \t10:09 \t10:19 \t10:29\n" +
                    "11:20 \t11:25 \t11:35 \t11:45\n" +
                    "11:55 \t12:00 \t12:10 \t12:20\n" +
                    "12:30 \t12:35 \t12:45 \t12:55\n" +
                    "13:05 \t13:10 \t13:20 \t13:30\n" +
                    "13:40 \t13:45 \t13:55 \t14:05\n" +
                    "14:20 \t14:25 \t14:35 \t14:45\n" +
                    "15:00 \t15:05 \t15:15 \t15:25\n" +
                    "15:40 \t15:45 \t15:55 \t16:05\n" +
                    "16:20 \t16:25 \t16:35 \t16:45\n" +
                    "17:00 \t17:05 \t17:15 \t17:25\n" +
                    "17:40 \t17:45 \t17:55 \t18:05\n" +
                    "18:20 \t18:25 \t18:35 \t18:45\n" +
                    "19:00 \t19:05 \t19:15 \t19:25\n" +
                    "19:40 \t19:45 \t19:55 \t20:05\n" +
                    "20:15 \t20:20 \t20:30 \t20:40\n" +
                    "20:50 \t20:55 \t21:05 \t21:15\n" +
                    "21:30 \t21:35 \t21:45 \t21:55";



    public String[] getBusStopsA(){
        return parserBusStops(busStopsA);
    }



    public String[] getTimeTableA(){
        return parserHorario(timeTableA);
    }

    /**
     * linea rio cuarto higueras
     * @return
     */

    public String[] getTimeTableRioHigueras() {
        return timeTableRioHigueras;
    }

    public String[] getBusStopsRioHigueras() {
        return busStopsRioHigueras;
    }

    public String[] getTimeTableRioHiguerasSyd() {
        return timeTableRioHiguerasSyd;
    }

    private String[] timeTableRioHiguerasSyd = makeArray("1 2 1 2 1 2 1 2\n" +
            "C:111 C:109 C:111 C:109 C:111 C:109 C:111 C:109\n" +
            "05:00\t- \t05:25\t- \t05:30\t- \t05:50\t -\n" +
            "06:00\t-\t06:25\t-\t06:30\t-\t06:50\t- \n" +
            "07:00\t-\t07:20\t-\t07:30\t-\t07:50\t- \n" +
            "7:50\t-\t8:10\t-\t8:15\t-\t8:40\t- \n" +
            "8:50\t-\t9:10\t-\t9:15\t-\t9:40\t- \n" +
            "9:50\t-\t10:10\t-\t10:15\t-\t10:40\t- \n" +
            "10:50\t-\t11:10\t-\t11:15\t-\t11:40\t- \n" +
            "12:00\t-\t12:20\t-\t12:25\t-\t12:50\t- \n" +
            "13:00\t-\t13:20\t-\t13:25\t-\t13:45\t- \n" +
            "13:45\t-\t14:05\t-\t14:10\t-\t14:35\t- \n" +
            "14:35\t-\t14:55\t-\t15:00\t-\t15:25\t- \n" +
            "15:30\t-\t15:50\t-\t16:55\t-\t16:20\t- \n" +
            "17:00\t-\t17:20\t-\t17:25\t-\t17:45\t- \n" +
            "18:00\t-\t18:20\t-\t18:25\t-\t18:50\t- \n" +
            "19:10\t-\t19:30\t-\t19:35\t-\t20:00\t -\n" +
            "20:05\t-\t20:25\t-\t20:30\t-\t20:55\t- \n" +
            "21:05\t-\t21:30\t-\t21:40\t-\t22:00\t- \n" +
            "22:10\t-\t22:35\t-\t22:45\t-\t23:05\t- \n" +
            "23:15\t-\t23:40\t-\t23:50\t-\t00:05\t- \n" +
            "00:30\t- \t00:50\t -\t01:00\t- \t01:15  -");

    private String[] busStopsRioHigueras = {"Term", "inal" ,"Base ", "Aerea" ,"Las Hi", "gueras" , "Río ", "Cuarto"};

    private String[] timeTableRioHigueras = makeArray("1 2 1 2 1 2 1 2\n" +
            "C:111 C:109 C:111 C:109 C:111 C:109 C:111 C:109\n" +
            "05:00\t- \t05:25\t- \t05:30\t- \t05:50 -\t \n" +
            "06:00\t-\t06:25\t-\t06:30\t-\t06:50 -\t \n" +
            "07:00\t07:00\t07:20\t07:15\t07:30\t07:30\t07:50\t07:45\n" +
            "08:05\t07:50\t08:30  -\t\t08:40\t08:20\t09:00\t08:35\n" +
            "09:10\t-\t09:35\t-\t09:45\t-\t10:05\t- \n" +
            "10:15\t-\t10:40\t-\t10:50\t-\t11:10\t- \n" +
            "11:20\t-\t11:45\t-\t11:55\t-\t12:25\t11:50\n" +
            "12:30\t12:00\t12:50\t12:25\t13:00\t12:35\t13:20\t12:55\n" +
            "13:30\t13:05\t13:55\t13:30\t14:05\t13:40\t14:25\t13:55\n" +
            "14:35\t14:00\t14:50\t14:25\t15:00\t14:30\t15:20\t14:50\n" +
            "15:40\t15:00\t16:05\t-\t16:15\t15:30\t16:35\t15:55\n" +
            "16:45\t16:05\t17:10\t16:30\t17:20\t16:35\t17:40\t16:55\n" +
            "17:50\t17:05\t18:15\t-\t18:25\t17:35\t18:45 -\t \n" +
            "18:55\t- \t19:20\t-\t19:30\t-\t19:50\t19:20\n" +
            "20:00\t19:30\t20:25\t-\t20:35\t20:00\t20:55\t20:20\n" +
            "21:05\t20:30\t21:30\t-\t21:40\t21:00\t22:00\t- \n" +
            "22:10\t-\t22:35\t-\t22:45\t-\t23:05\t -\n" +
            "23:15\t-\t23:40\t-\t23:50\t-\t00:10\t -\n" +
            "00:30\t-\t00:50\t-\t01:00\t-\t01:15    -");


    /**
     * holmberg
     *
     */

    private String[] headerRioCuartoHolmberg={"Rio Cuarto","Holmberg","Rio Cuarto"};
    private String[] timeTableRioCuartoHolmberg={
            "05:00","05:25","06:00",
            "05:30","06:00","06:30",
            "06:05","06:35","07:10",
            "06:40","07:15","07:50",
            "07:10","07:45","08:25",
            "07:50",   "08:25",   "09:10",
            "08:25",   "09:05",   "09:45",
            "09:10",   "09:45",   "10:25",
            "09:45",   "10:25",   "11:05",
            "10:25",   "11:05",   "11:45",
            "11:05",   "11:45",   "12:25",
            "11:45",   "12:25",   "13:05",
            "12:30",   "13:08",   "13:45",
            "13:10",   "13:44",   "14:25",
            "13:45",   "14:20",   "14:55",
            "14:25",   "15:05",   "15:45",
            "15:05",   "15:45",   "16:25",
            "15:45",   "16:25",   "17:05",
            "16:25",   "17:05",   "17:45",
            "17:05",   "17:45",   "18:25",
            "17:50",   "18:33",   "19:10",
            "18:25",   "19:05",   "19:45",
            "19:10",   "19:40",   "20:10",
            "19:45",   "20:25",   "20:50",
            "20:10",   "20:40",   "21:10",
            "20:50",   "21:20",   "21:45",
            "21:45",   "22:19",   "23:00",
            "23:00",   "23:35",   "0:20",
            "0:40",   "1:10",  "1:45"

    };
    private String[] timeTableRioCuartoHolmbergSab={
            "05:00","05:25","06:00",
            "05:30","06:00","06:30",
            "06:10",   "06:40",  "07:10",
            "06:40",   "07:15",   "07:50",
            "07:10",   "07:45",   "08:25",
            "07:50",   "08:25",   "09:10",
            "08:25",   "09:05",   "09:45",
            "09:10",   "09:45",   "10:25",
            "09:45",   "10:25",   "11:05",
            "10:25",   "11:05",   "11:45",
            "11:05",   "11:45",   "12:25",
            "11:45",   "12:25",   "13:05",
            "12:30",   "13:08",   "13:45",
            "13:10",   "13:45",   "14:25",
            "13:45",   "14:20",   "14:55",
            "15:05",   "15:45",   "16:25",
            "16:25",   "17:05",   "17:45",
            "17:50",   "18:33",   "19:10",
            "19:10",   "19:40",   "20:10",
            "20:10",   "20:40",  "21:10",
            "21:45",   "22:20",   "23:00",
            "23:00",   "23:35",   "0:20",
            "01:00",   "01:30",   "02:00"
    };
    private String[] timeTableRioCuartoHolmbergDom={
            "05:00","05:25","06:00",
            "06:10","06:40","07:10",
            "07:10" ,  "07:45"   ,"08:25",
            "08:25"  , "09:05"   ,"09:45",
            "09:45"   ,"10:25"   ,"11:05",
            "11:05"   ,"11:45"   ,"12:25",
            "12:30"   ,"13:08"   ,"13:45",
            "13:45"   ,"14:20"   ,"14:55",
            "15:05"   ,"15:45"   ,"16:25",
            "16:25"   ,"17:05"   ,"17:45",
            "17:50"   ,"18:38"   ,"19:10",
            "19:10"   ,"19:40"   ,"20:10",
            "20:10"   ,"20:40"   ,"21:10",
            "21:45"   ,"22:20"   ,"23:00",
            "23:00"   ,"23:35"   ,"0:20",
            "01:00"   ,"01:30"   ,"02:00"
    };

    public String[] getHeaderRioCuartoHolmberg() {return headerRioCuartoHolmberg; }

    public String[] getTimeTableRioCuartoHolmberg() { return timeTableRioCuartoHolmberg; }

    public String[] getTimeTableRioCuartoHolmbergSab() { return timeTableRioCuartoHolmbergSab;  }

    public String[] getTimeTableRioCuartoHolmbergDom() { return timeTableRioCuartoHolmbergDom; }

    private int cantBondi1V =3 ; //findes ytambien
    private int cantBondi1R =3 ; //findes tambien!
    private int cantBondi2V = 6;
    private int cantBondi2VFin = 4;
    private int cantBondi2VE = 3;


    private int cantBondi2R = 3;
    private int cantBondi2RFin = 2;
    private int cantBondi3 = 2;
    private int cantBondi4 = 1;

    private int cantBondi5 =4 ;
    private int cantBondi5Fin =2 ;
    private int cantBondi5Ver =3 ;

    private int cantBondi6= 1;
    private int cantBondi6E= 1;

    private int cantBondi7= 1;

    private int cantBondi13= 4;
    private int cantBondi13Fin= 2;
    private int cantBondi18=2 ;

    public int getCantBondi1V() {
        return cantBondi1V;
    }

    public int getCantBondi1R() {
        return cantBondi1R;
    }


    public int getCantBondi2RFin() {
        return cantBondi2RFin;
    }

    public int getCantBondi2R() {
        return cantBondi2R;
    }

    public int getCantBondi2V() {
        return cantBondi2V;
    }

    public int getCantBondi2VE() {
        return cantBondi2VE;
    }

    public int getCantBondi2VFin() {
        return cantBondi2VFin;
    }

    public int getCantBondi3() {
        return cantBondi3;
    }

    public int getCantBondi4() {
        return cantBondi4;
    }

    public int getCantBondi5Fin() {
        return cantBondi5Fin;
    }

    public int getCantBondi5() {
        return cantBondi5;
    }

    public int getCantBondi5Ver() {
        return cantBondi5Ver;
    }

    public int getCantBondi6() {
        return cantBondi6;
    }

    public int getCantBondi6E() {
        return cantBondi6E;
    }


    public int getCantBondi13() {
        return cantBondi13;
    }

    public int getCantBondi13Fin() {
        return cantBondi13Fin;
    }

    public int getCantBondi18() {
        return cantBondi18;
    }
}
