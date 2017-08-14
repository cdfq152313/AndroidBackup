package com.example.denny.ticketparser;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(Theories.class)
public class ExampleUnitTest {

    static int count = 0;

    @DataPoints
    public static String[] tickets ={
            "M1TENG/CHANG JUNG     E2XMHSJ KEFOSLFI 0318 167M020E0225 32C>2180      B   01088936600010E             0",
            "M1TENG/CHANGJUNG MR   E2R8UKH LHRHKGBA 0027 170M037J0094 149>5181FF7170BBA              2A1258751700673N1   BA 16859399            N8",
            "M1CHEN/CHUN SHEN      E63IUUF TPEHKGCX 0465 158Y039D0099 34A>1180 O    B                29                                         8  ",
            "M1CHEN/CHUNSHEN MR    E2R8UKH LHRHKGBA 0027 170M000 0092 749>5181FF7170BBA              2A1258751700669N1                          N8",
            "M1TENG/CHANGJUNG MR   E2R8UKH CPHLHRBA 0811 170M008C0140 149>5181FF7170BBA              2A1258751700672N1   BA 16859399            N8",
            "M1CHANG/LI CHIN       E2XMHSJ KEFOSLFI 0318 167M016F0228 31C>2080      B0E             0",
            "M1CHEN/CHUN SHEN      E63IUUF HKGTPECX 0468 171Y039C0265 34A>1180 O    B                29                                          8  ",
            "M1CHANG/LICHIN MS     E2R8UKH CPHLHRBA 0811 170M005F0137 149>5181FF7170BBA              2A1258751700666N1                          N8",
            "M1CHANG/LICHIN MS     E2R8UKH LHRHKGBA 0027 170M000 0091 749>5181FF7170BBA              2A1258751700667N1                          N8",
            "M1TENG/CHANG JUNG     E63IUUF TPEHKGCX 0465 158Y040J0095 34A>1180 O    B                29                                         8  ",
            "M1TENG/PEICHIH        EOCIEYO HKGTPECX 0468 171Y040K0268 34A>1180 O    B                29                  CX 1561906564          8GR",
            "M1KUO/YICHENG         EYGJV75 TPEHKGCX 0407 193Y039B0189 34A>1180      B                29                                         8  ",
            "M1TENG/PEICHIH MR     E2R8UKH CPHLHRBA 0811 170M007A0141 149>5181FF7170BBA              2A1258751700664N1   CX 1561906564          N8",
            "M1TENG/CHANGJUNG MR   E2R8UKH LHRHKGBA 0027 170M000 0094 749>5181FF7170BBA              2A1258751700673N1   BA 16859399            N8",
            "M1CHEN/HAOWEN         EOC5DUB TPEHKGCX 0407 156Y043K0120 34A>1180 O    B                29                                         8  ",
            "M1KUO/YICHENG         E4JB7D9 ICNTPEBR 0169 178Y044A0048 35C>2180  0178BBR              3E69524327160800                           *30600000K09         ",
            "M1CHEN/CHUN SHEN      E63IUUF LHRBGOBA 0760 159M005C0007 34A>1180 O    B                29                                         8  ",
            "M1CHEN/HAOWEN         EOC5DUB HKGTPECX 0450 158Y067A0140 14E>10B0KK    B   29                                         8                   ",
            "M1TENG/PEICHIH MR     E2R8UKH LHRBGOBA 0760 159M005A0052 149>5181FF7159BBA              2A1258751700664N1   CX 1561906564          N8",
            "M1KUO/YICHENG         EZ8CRMZ SEATPEBR 0025 310Y056B0091 35C>2180  5310BBR 06958545600013E69524358558360                           *30601023K09         ",
            "M1TENG/PEICHIH MR     E2R8UKH LHRHKGBA 0027 170M037K0095 149>5181FF7170BBA              2A1258751700665N1   CX 1561906564          N8",
            "M1TENG/PEI CHIH       E2XMHSJ KEFOSLFI 0318 167M020D0224 343>2180      B   010889365900125             0    FI 4701290241      ",
            "M1CHANG/LI CHIN       E63IUUF LHRBGOBA 0760 159M005B0006 34A>1180 O    B                29                                         8  ",
            "M1CHEN/CHUN SHEN      E2XMHSJ KEFOSLFI 0318 167M016G0227 31C>2080      B0E             0",
            "M1TENG/CHANG JUNG     E63IUUF LHRBGOBA 0760 159M005F0005 34A>1180 O    B                29                                         8  ",
            "M1TENG/PEICHIH        E8SGE2T HKGTPECX 0406 004Y049K0054 34A>1180 O    B                29                  CX 1561906564          8GR",
            "M1TENG/PEICHIH        E8SGE2T BKKHKGCX 0708 003Y043H0257 14E>10B0MW7003BCX 291602356196565 1CX CX 1561906564       20K8GR708",
            "M1CHOI/MOONYOUNG      EYBS7IC ICNJFKKE 0081 009Y035B0033 34C>5180  7008B1A 01803451270012A18011460826170 KE KE 114528792809        NTB",
            "M1ARIEFBASLY  MUHAMMAD YPFFUB PDGHLPID 7106 008Z002F0014",
            "M1CHEN/HUNGHUA        ELLTGBB EUGSFOUA 5392 096Y00000000 15C>3180 M6096BUA 40166368870022901677527786970 UA                        *30602    09         ^160MEUCIDIwH5XYzY5434h14zV6NIFTEtIDHVoldR4/o502EZ97AiEAzicMLPnzhFagyYptbsF1KKYG4K4snDWAFMS1p7znC0I=",
            "M3CHEN/HUNG HUA       EJBZXGT LAXSTSAS 2472 089Y003E0000 148>2181MM    BAS              25             0                       Z64B JBZXGT STSPDXAS 2460 089Y005A0000 12c25             0                       Z    JBZXGT PDXEUGAS 2035 089Y016B0000 13125             0                       Z    00010^460MEUCIQDggey0duJ3VYYCMhKn8KU8t0Uh94sxSLt5QWc9dVxdYQIgUm44fmksE6qS1Z8/Aee9f54DNaQLPELp7GCKIzIbMDM=",
            "M1KUO/YICHENG         EYHF5KO HKGTPEKA 0486 201Y030C0119 34A>1180      B                29                                         8",
            "M1HSU/CHINGYI         E8SGE2T HKGBKKCX 0703 361Y075K0144 14E>10B0KK    B   29                  CX 1732213849          8AM",
            "M1KUO/YICHENG         E6D423J HKGTPECX 0450 155Y079G0302 34A>1180      B                29                                         8",
            "M1KUO/YI CHENG        E27J6KP HKGTPEBR 0868 283Y055H0043 35D>5180  6283BBR 06958167100012A69524388230590 BR                        N*30601013K09",
            "M1KUO/YICHENG         E596CP5 TPEHKGCX 0467 303Y059F0160 14E>10B0KK    B   29                  CX 1730196096          8AM",
            "M1KUO/YICHENG         EYHF5KO TPEHKGCX 0467 205Y042H0259 14E>10B0KK    B   29                                         8",
            "M1KUO/YICHENG         E4JB7D9 TPEICNBR 0170 174Y050A0053 35C>2180  0173BBR              3E69524327160800                           *30600000K09",
    };

    @Theory
    public void formatTicket(String ticket){
        System.out.println( String.format("======= Test Case: %d =======", count++) );
        TicketData data = TicketParser.parse(ticket);
        System.out.println( data.toString() );
    }

    @Test
    public void julianDate(){
        System.out.println(JulianToFormatDate.convert2(200));
    }
}

