package org.tecpro.colectivos;

/**
 * Created by agustin on 11/04/14.
 */
public class Horarios {

    private String[] busStops1verde={"Va. Dalcar","Plaza","Bº Peyrano","Plaza"};

    private String[] timeTable1verde={
            "05:15","05:23","05:32","05:43",
            "","","06:20","06:37",
            "06:00","06:18","06:37","06:55",
            "06:25","06:42","07:01","07:20",
            "06:48","07:07","07:27","07:50",
            "07:15","07:33","07:53","08:16",
             "07:40","07:58","08:21","09:44",
            "08:10","08:27","08:50","09:13",
            "08:36","09:55","09:18","09:41",
            "09:04	","09:23","09:46","10:09",
            "09:33	","09:51","10:14","10:37",
            "10:01	","10:19","10:42","11:05",
            "10:29	","10:47","11:10","11:33",
            "10:57	","11:15","11:38","12:01",
            "11:25","11:43","12:06","12:29",
            "11:53","12:11","12:34","12:57",
            "12:21","12:39","13:02","13:25",
            "12:49","13:07","13:30","13:53",
            "13:17	","13:35","13:58","14:21",
            "13:45	","12:03","14:26","14:49",
            "14:13	","14:31","14:54","15:17",
            "14:41	","14:59","15:22","15:45",
            "15:09	","15:27","15:50","16:13",
            "15:37	","15:55","16:18","16:41",
            "18:05	","16:23","16:46","17:09",
            "13:33	","16:51","17:14","17:37",
            "17:01","17:19","17:42","18:05",
            "17:29	","17:47","18:10","18:33",
            "17:57	","18:15","18:38","19:01",
            "18:25","18:43","19:06","19:29",
            "18:53","19:11","19:34","19:57",
            "19:21","19:39","20:02","20:25",
            "19:49","20:07","20:27","20:47",
            "20:17","20:35","20:55","21:15",
            "20:45","21:03","21:14","21:44",
            "21:07","21:25","21:45","22:05",
            "22:00","22:12","22:29	","22:44",
            "23:00","23:12","23:29","23:44",
            "00:00","00:12","00:29","00:44"
            };

    private String[] header13={"PLAZA","UNRC","PLAZA","COMUNIC","CEMENT" };
    private String[] timeTable13={"","","","06:10","06:20","","","06:20","06:30","06:43","06:00","06:20","06:40","06:50","07:02","06:20","06:40","07:00","07:12","07:26","06:31","06:55","07:20","07:33","07:50","07:00","07:20","07:40","07:53","08:10","07:16","07:41","08:06","08:19","08:36","07:39","08:04","08:29","08:42","08:59","08:03","08:28","08:53","09:06","09:23","08:26","08:51","09:16","09:29","09:46","08:50","09:15","09:40","09:53","10:10","09:13","09:38","10:03","10:16","10:33","09:37","10:02","10:27","10:40","10:57","10:00","10:25","10:50","11:03","11:20","10:24","10:49","11:14","11:27","11:44","10:47","11:12","11:37","11:50","12:07","11:11","11:36","12:01","12:14","12:31","11:34","11:59","12:24","12:37","12:54","11:58","12:23","12:48","13:01","13:18","12:21","12:46","13:11","13:24","13:41","12:45","13:10","13:35","13:48","14:05","13:08","13:33","13:58","14:11","14:28","13:32","13:57","14:22","14:35","14:52","13:55","14:20","14:45","14:58","15:15","14:19","14:44","15:09","15:22","15:39","14:42","15:07","15:32","15:45","16:02","15:06","15:31","15:56","16:09","16:26","15:29","15:54","16:19","16:32","16:49","15:53","16:18","16:43","16:56","17:13","16:16","16:41","17:06","17:19","17:36","16:40","17:05","17:30","17:43","18:00","17:03","17:28","17:53","18:06","18:23","17:27","17:52","18:17","18:30","18:47","17:50","18:15","18:40","18:53","19:10","18:14","18:39","19:04","19:17","19:34","18:37","19:02","19:27","19:40","19:57","19:01","19:26","19:51","20:04","20:21","19:24","19:49","20:14","20:27","20:44","19:48","20:13","20:38","20:51","21:08","20:11","20:36","21:01","21:14","21:31","20:35","21:00","21:25","21:38","","20:58","21:23","21:48","22:01","","21:22","","","","","21:45","","","",""};
    private String[] header13Fin={"PLAZA ","ACOR","UNRC.","ACOR","PLAZA ","COMUN","CEMEN"};
    private String[] timeTable13Fin={ ""," "," "," "," ","6:10","6:20","6:00","6:10","6:20","6:30","6:40","6:52","7:07","6:35","6:48","6:55","7:05","7:20","7:32","7:47","7:20","7:30","7:40","7:50","8:00","8:12","8:27","8:00","8:10","8:20","8:30","8:40","8:52","9:07","8:40","8:50","9:00","9:00","9:20","9:32","9:47","9:20","9:30","9:40","9:50","10:00","10:12","10:27","10:00","10:10","10:20","10:30","10:40","10:52","11:07","10:40","10:50","11:00","11:10","11:20","11:32","11:47","11:20","11:30","11:40","11:50","12:00","12:12","12:27","12:00","12:10","12:20","12:30","12:40","12:52","13:07","12:40","12:50","13:00","13:10","13:20","13:32","13:47","13:20","13:30","13:40","13:50","14:00","14:12","14:27","14:00","14:10","14:20","14:30","14:40","14:52","15:07","14:40","14:50","15:00","15:10","15:20","15:32","15:47","15:20","15:30","15:40","15:50","16:00","16:12","16:27","16:00","16:10","16:20","16:30","16:40","16:52","17:07","16:40","16:50","17:00","17:10","17:20","17:32","17:47","17:20","17:30","17:40","17:50","18:00","18:12","18:27","18:00","18:10","18:20","18:30","18:40","18:52","19:07","18:40","18:40","19:00","19:10","19:20","19:32","19:47","19:20","19:30","19:40","19:50","20:00","20:12","20:27","20:00","20:10","20:20","20:30","20:40","20:42","20:47","20:40","20:50","21:00","21:10","21:20","21:30","21:35","21:20","21:30","21:40","21:50","22:00","22:12","22:48"};

    private String[] header6={"EPEC","ACORD","UNRC","ACORD","PLAZA",	"H.CENT.",	"I.P.V."};
    private String[] timeTable6={ " ", " ", " ", " ", " ", "5:32", "5:37", "5:47", "5:59", "6:05", "6:12", "6:20", "6:35", "6:40", "6:53", "7:05", "7:30", "7:39", "7:53", "8:07", "8:15", "8:30", "8:42", "8:50", "8:58", "9:10", "9:27", "9:35", "9:50", "10:02", "10:10", "10:18", "10:30", "10:47", "10:55", "11:10", "11:22", "11:30", "11:38", "11:50", "12:07", "12:15", "12:30", "12:42", "12:50", "12:58", "13:10", "13:25", "13:30", "13:45", "13:57", "14:05", "14:13", "14:23", "14:37", "14:42", "14:55", "15:07", "15:30", "15:38", "15:50", "16:07", "16:12", "16:25", "16:42", "16:50", "16:58", "17:10", "17:27", "17:35", "17:50", "18:02", "18:10", "18:18", "18:30", "18:47", "18:55", "19:10", "19:22", "19:30", "19:38", "19:50", "20:07", "20:15", "20:30", "20:42", "20:50", "20:58", "21:10", "21:25", "21:30", "21:45", "21:55", "22:05", "21:12", "22:20", "22:30", "22:35"};
    private String[] timeTable6Esp={ "", " ", " ", "22:10", "22:25", "22:45", "23:00", "23:10", "23:25", "23:45", "00:00", "00:10", "00:25", "00:45", "01:00", "01:10"};
    private String[] header6Esp={"Plaza", "UNRC", "Plaza", "H. Central"};

    private String[] header7={"CINEMA", "PILET.", "IPV", "PILET.", "PLAZA", "COMUN", "TIRO FED."};
    private String[] timeTable7={"05:25", "05:35", " ", " ", "05:45", "05:52", "06:05", " ", " ", " ", " ", " ", "05:30", "05:40", "05:49", "05:55", "06:00", "06:05", "06:17", "06:30", "06:43", "06:20", "06:30", " ", " ", "06:50", "07:00", "07:11", "06:56", "07:05", "07:10", "07:27", "07:37", "07:50", "08:02", "07:22", "07:27", "07:35", "07:43", "08:03", "08:15", "08:30", "08:16", "08:38", "08:43", "08:52", "09:03", "09:16", "09:29", "09:42", "10:03", "10:08", "10:13", "10:33", "10:46", "10:59", "11:12", "11:33", "11:38", "11:43", "12:03", "12:16", "12.29", "11:55", "12:05", "12:17", "12:23", "12:45", "13:01", "13:14", "12:42", "13:03", "13:12", "13:17", "13:38", "13:46", "13:59", "13:27", "13:48", "13:53", " ", " ", " ", " ", "14:12", "14:33", "14:38", "14:43", "15:03", "15:16", "15:29", "15:42", "16:03", "16:08", "16:13", "16:33", "16:46", "16:59", "17:12", "17:33", "17:38", "17:43", "18:03", "18:16", "18:29", " ", " ", "18:17", "18:23", "18:45", "19:00", "19:18", "18:42", "19:03", "19:08", "19:13", "19:33", "19:46", "19:59", "19:27", "19:37", "19:47", "19:53", "20:15", "20:30", "20:45","20:12", "20:33", "20:38", "20:43", "21:03", "21:16", "21:29", "21:36", "21:50", " ", " ", " ", " ", " ", " ", " ", " "};

    private String[] header7Fin={"CINEMA","PILET.","IPV", "PILET.","PLAZA","COMUN","TIRO FED."};
    private String[] timeTable7Fin={ ""," "," "," "," ","05:30","05:40","05:49","05:55","06:00","06:05","06:17","06:30","06:43","06:56","07:05","07:10","07:27","07:37","07:50","08:02","08:16","08:38","08:43","08:52","09:03","09:16","09:29","09:42","10:03","10:08","10:13","10:33","10:46","10:59","11:12","11:33","11:38","11:43","12:03","12:16","12:29","12:42","13:03","13:12","13:17","13:38","13:46","13:59","14:12","14:33","14:38","14:43","15:03","15:16","15:29","15:42","16:03","16:08","16:13","16:33","16:46","16:59","17:12","17:33","17:38","17:43","18:03","18:16","18:29","18:42","19:03","19:08","19:13","19:33","19:46","19:59","20:12","20:33","20:38","20:43","21:03","21:16","21:29","21:36","21:50"," "," "," "," "," ",""};

    private String[] header8V={"PLAZA","UNRC","PLAZA","V. DALCAR","ANTENA","GOLF"};
    private String[] timeTable8V={ ""," "," ","06:22"," ","06:29","06:39","07:04","07:28","07:45","S.E. 07:53","08:00","06:10","06:26","06:45","06:57","07:05","07:10","07:23","07:48","08:12","08:30","08:38","08:43","08:59","09:26","09:54","10:11"," ","10:21","08:10","P. 08:37","09:05","09:20","09:29","09:34","09:48","10:15","10:43","11:00"," ","11:10","10:37","P. 11:04","11:32","11:49"," ","11:59","11:26","11:53","12:21","12:36","12:45","12:50","12:15","P. 12:42","13:10","13:25","13:32","13:37","13:04","13:31","13:59","14:14","14:23","14:28","13:53","P. 14:20","14:48","15:05"," ","15:15","14:42","15:09","15:37","15:54"," ","16:04","15:31","15:58","16:26","16:43"," ","16:53","16:20","16:47","17:15","17:32"," ","17:42","17:09","17:36","18:04","18:21","18:28","18:33","17:58","P. 18:25","18:53","19:10"," ","19:20","18:47","19:14","19:42","20:00"," ","20:09","19:36","20:03","20:31","20:48"," ","20:58","20:25","20:52","21:20","21:37"," ","21:47","21:14","21:40","22:05","22:15"," "," ","22:01","22:18"," "," "," "," ",""};

    private String[] header8VSab={"PLAZA","UNRC","PLAZA","V. DALCAR","ANTENA","GOLF"};
    private String[] timeTable8VSab={ ""," ","06:25","06:35"," ","06:45","06:55","P. 07:26","07:46","08:00"," ","08:10","08:30","P. 08:53","09:16","09:30","09:39","09:44","10:00","P. 10:23","10:46","11:00","11:09","11:14","11:30","11:53","12:16","13:30","12:39","12:44","13:00","13:23","13:46","14:00"," ","14:14","14:30","P. 14:53","15:16","15:30","15:39","15:44","16:00","16:23","16:46","17:00","17:09","17:14","17:30","17:53","18:16","18:30"," ","18:44","19:00","P. 19:23","19:46","20:00","20:09","20:14","20:30","20:53","21:16","21:30"," ","21:44"};

    private String[] header8R={"PLAZA","UNRC","PLAZA","GOLF","ANTENA","V. DALCAR"};
    private String[] timeTable8R={ ""," ","05:55","06:07"," ","06:10"," "," ","06:23","06:37"," ","06:45","06:17","06:42","07:06","07:18","07:23","07:31","06:57","P. 07:26","07:50","08:04"," ","08:14","07:47","08:14","08:42","08:58"," ","09:08","08:34","09:01","09:29","09:45"," ","09:55","09:23","P. 09:50","10:18","10:30","10:36","10:46","10:12","10:39","11:07","11:19","11:24","11:33","11:01","11:28","11:56","12:12"," ","12:22","11:50","12:17","12:45","13:01"," ","13:11","12:39","13:06","13:34","13:50"," ","14:00","13:28","13:55","14:23","14:39"," ","14:49","14:17","14:44","15:12","15:26","15:31","15:40","15:06","P. 15:33","16:01","16:15","16:18","16:27","15:55","P. 16:22","16:50","17:06"," ","17:16","16:44","17:11","17:39","17:55"," ","18:05","17:33","18:00","18:28","18:44"," ","18:54","18:22","18:49","19:17","19:19","19:34","19:43","19:11","P. 19:38","20:06","20:09","20:24","20:34","20:00","P. 20:27","20:55","21:11"," ","21:21","20:49","P. 21:14","21:38","21:50"," ","22:00","21:38","22:04","22:20","22:32"," ","" };

    private String[] header8RSab={"PLAZA","UNRC","PLAZA","GOLF","ANTENA","V. DALCAR"};
    private String[] timeTable8RSab={"06:10","06:25","06:45","06:57","07:01","07:10","06:37","06:57","07:17","07:31","07:36","07:44","07:30","07:53","08:16","08:30","08:35","08:44","08:00","08:23","08:46","09:00","09:05","09:14","09:00","09:23","09:46","10:00","10:05","10:14","09:30","09:53","10:16","10:30"," ","10:44","10:30","10:53","11:16","11:30"," ","11:44","11:00","P. 11:23","11:46","12:00"," ","12:14","12:00","12:23","12:46","13:00","13:05","13:14","12:30","P. 12:53","13:16","13:30"," ","13:44","13:30","13:53","14:16","14:30","14:35","14:44","14:00","14:23","14:46","15:00"," ","15:14","15:00","15:23","15:46","16:00"," ","16:14","15:30","P. 15:53","16:16","16:30","16:36","16:44","16:30","16:53","17:16","17:30"," ","17:44","17:00","17:23","17:46","18:00","18:05","18:14","18:00","P. 18:23","18:46","19:00"," ","19:14","18:30","18:53","19:16","19:30","19:35","19:44","19:30","P. 19:53","20:16","20:30"," ","20:44","20:00","20:23","20:46","21:00"," ","21:14","21:00","P. 21:23","21:46"," "," "," ","21:30","21:53"," "," "," "," "};

    private String[] header8RDom={"PLAZA","UNRC","PLAZA","GOLF","ANTENA","V. DALCAR"};
    private String[] timeTable8RDom={ ""," ","06:20","06:35"," ","06:45","06:20","06:40","07:00","07:13","07:18","07:26","07:00","P. 07:20","07:40","07:55"," ","08:05","07:40","08:02","08:25","08:39","08:44","08:54","08:25","P. 08:47","09:10","09:24"," ","09:39","09:10","09:32","09:55","10:09","10:14","10:24","09:55","P. 10:17","10:40","10:54"," ","11:09","10:40","11:02","11:25","11:39","11:44","11:54","11:25","P. 11:47","12:10","12:24"," ","12:39","12:10","12:32","12:55","13:09","13:14","13:24","12:55","P. 13:17","13:40","13:54"," ","14:09","13:40","14:02","14:25","14:39","14:44","14:54","14:25","P. 14:47","15:10","15:24"," ","15:39","15:10","15:32","15:55","16:09","16:14","16:24","15:55","P. 16:17","16:40","16:54"," ","17:09","16:40","17:02","17:25","17:39","17:14","17:54","17:25","P. 17:47","18:10","18:24"," ","18:39","18:10","18:32","18:55","19:09","19:14","19:24","18:55","P. 19:17","19:40","19:54"," ","20:09","19:40","20:02","20:25","20:39","20:44","20:54","20:25","P. 20:47","21:10","21:24"," ","21:39","21:10","21:32","21:55","22:09"," ","22:24","21:55","22:17"," "," "," ","" };

    private String[] header9V={"EPEC","IPV.BN","UNRC","M.ABA","PLAZA","IPV.ALB"};
    private String[] timeTable9V={ ""," "," "," ","5:57","6:18","6:00","6:15","6:27","6:42","6:58","7:19","6:39","7:00","7:19","7:39","7:56","8:18","7:40","8:01","8:20","8:39","8:56","9:18","8:39","8:58","9:17","9:37","9:53","10:14","9:39","9:58","10:13","10:33","10:49","11:10","10:35","10:54","11:09","11:29","11:45","12:06","11:31","11:50","12:05","12:25","12:41","13:02","12:27","12:46","13:01","13:21","13:37","13:58","13:23","14:42","14:03","14:23","14:37","14:58","14:19","14:38","14:53","15:13","15:27","15:48","15:15","15:32","15:49","16:09","16:25","16:46","16:11","16:30","16:45","17:05","17:21","17:42","17:07","17:26","17:41","18:01","18:17","18:38","18:03","18:22","18:37","18:57","19:13","19:34","18:59","19:18","19:33","19:53","20:09","20:30","9:53","20:12","20:27","20:47","21:02","21:23","20:47","21:05","21:19","21:39","21:52","22:13","21:43","21:58","22:11",""};

    private String[] header9R={"EPEC","IPV.BN","UNRC","M.ABA","PLAZA","IPV.ALB"};
    private String[] timeTable9R={"","",""," ","5:55","6:05","5:30","5:48","6:00","6:13","6:28","6:50","6:25","6:39","6:50","7:07","7:20","7:39","7:11","7:28","7:51","8:11","8:27","8:50","8:02","8:21","8:49","9:09","9:25","9:46","9:11","9:30","9:45","10:05","10:21","10:42","10:07","10:26","10:40","11:00","11:17","11:38","11:03","11:22","11:37","11:57","12:13","12:34","11:59","12:18","12:33","12:53","13:09","13:30","12:55","13:14","13:29","13:49","14:05","14:26","13:51","14:10","14:25","14:45","14:57","15:18","14:47","15:06","15:21","15:41","15:57","16:18","15:43","16:02","16:16","16:36","16:53","17:14","16:39","16:58","17:13","17:33","17:49","18:10","17:35","17:54","18:09","18:29","18:45","19:06","8:31","18:50","19:05","19:25","19:41","20:02","19:27","19:46","20:01","20:21","20:35","20:56","20:20","20:39","20:52","21:12","21:27","21:48","21:15","21:34","21:46","22:00","22:17","22:38","22:10","22:20","22:30"," "," "," "};


    public String[] getTimeTable1verde(){
        return timeTable1verde;
    }
     public String[] getHeader1verde(){
         return busStops1verde;
     }

    public String[] getHeader13() {
        return header13;
    }

    public String[] getTimeTable13() {
        return timeTable13;
    }

    public String[] getHeader6() {
        return header6;
    }

    public String[] getTimeTable6() {
        return timeTable6;
    }

    public String[] getTimeTable6Esp() {
        return timeTable6Esp;
    }

    public String[] getHeader6Esp() {
        return header6Esp;
    }

    public String[] getHeader7() {
        return header7;
    }

    public String[] getTimeTable7() {
        return timeTable7;
    }

    public String[] getHeader7Fin() {
        return header7Fin;
    }

    public String[] getTimeTable7Fin() {
        return timeTable7Fin;
    }

    public String[] getTimeTable8V() {
        return timeTable8V;
    }

    public String[] getHeader8V() {
        return header8V;
    }

    public String[] getHeader8VSab() {
        return header8VSab;
    }

    public String[] getTimeTable8VSab() {
        return timeTable8VSab;
    }

    public String[] getHeader8R() {
        return header8R;
    }

    public String[] getTimeTable8R() {
        return timeTable8R;
    }

    public String[] getTimeTable8RSab() {
        return timeTable8RSab;
    }

    public String[] getHeader8RSab() {
        return header8RSab;
    }

    public String[] getTimeTable8RDom() {
        return timeTable8RDom;
    }

    public String[] getHeader8RDom() {
        return header8RDom;
    }

    public String[] getHeader9V() {
        return header9V;
    }

    public String[] getTimeTable9V() {
        return timeTable9V;
    }

    public String[] getHeader9R() {
        return header9R;
    }

    public String[] getTimeTable9R() {
        return timeTable9R;
    }

    public String[] getHeader13Fin() {
        return header13Fin;
    }

    public String[] getTimeTable13Fin() {
        return timeTable13Fin;
    }
}