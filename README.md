## 1. How to run the program.

    ## mvn clean
    ## mvn install
    ## mvn package
    ## jar -jar target/inchi-0.1.jar
    
    The urls are set in Utils.java. The program can download files automately. You can download the files and mv them to input dir if you want.

## 2. Time complexity of the solution.

    This is a computing sensitive problem. The main problem is the TopN, and the sub problem is the pattern detection.

    Dict has 178690 words(0.2m). We can set the data in cache and sort the data by word.length.
    Caching and sorting the dict data is good for TopN problem, and is good for distributing computing.

    Chembl has 1583898(1.5m) lines. It can be put in memory. We collect topN for every line, merge the data, sort the data and get the TopN.

    For pattern detection, I use the Boyer-Moore string search algorithm.
    If we ignore the TopN, we can get the time complexity.
    Let N is the number of Chembl lines.
    Let M is the number of Dict lines.
    The time complexity is O(s*N*M), s can be thought a constant number, which is 30 approximate.

    When N = 1.5m, M = 0.2m and s = 30, the bigO is 900b approximate.

    The main method is the execute() in InChi.java.

## 3. How to run the test and how to interpret the results.

    3.1 how to run the test:

        3.1.1 how to run
            # mvn test

        3.1.2 InChiTest: testing the performance
        3.1.3 BoyerMooreTest: testing the BM algorithm.
        3.1.4 UtilsTest: testing some util tools.

    3.2 I selected 1 group data. The data is in the test directory.

     n is the number of Chembl. m is the number of words.

     QYYCFCXQJFGQJQ-NBEYISGCSA-N, QYYCFCXQJFGQJQ, CHEMBL100991
     QFKHZABETHFMHQ-MBFMUOJQSA-N, ABET, CHEMBL101684
     ABOSKNBFTLZPNO-UHFFFAOYSA-N, ABOS, CHEMBL102581
     HCABSDMEPTYQBN-HOTGVXAUSA-N, ABS, CHEMBL100147
     VGWABONFQSJGCE-JYNPSLGESA-M, ABO, CHEMBL100188
     QPWYZQIIZHEABR-IRWQIABSSA-N, ABS, CHEMBL100429
     KGSOKXYVAASSHW-UHFFFAOYSA-N, AAS, CHEMBL100490
     VMDPKVPTUTUSNH-SZABORAWSA-N, ABO, CHEMBL10059
     PVZJYXVABSUAGS-UHFFFAOYSA-N, ABS, CHEMBL101154
     WYGWAASOIXQQJK-UHFFFAOYSA-N, AAS, CHEMBL101289
     n = 4000 , m = 1000, time: 1151

     The cost obeys our analysis approximately.

## 4. Programming process

     4.1 Downloading data
     4.2 Loading dict data.
     4.3 Loading inchi data.
     4.4 Computing.
     4.5 Printing the result.
