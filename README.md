 AUTHOR: SIDDHANT DESHMUKH
 UFID  : 36568046
-------------------------------------------------------
 DISTRIBUTED OPERATING SYSTEM- BITCOIN MINING PROJECT  
-------------------------------------------------------

CONTENTS OF THIS FILE 
---------------------
   
 * Introduction
 * Requirements
 * Installation and Configuration
 * Program flow
 * Project Questions Answered
 * Developer information



INTRODUCTION
------------
The project folder dos-siddhant contains 3 folders:

* BitClient: Contains all the files and folders needed to run program on the client side. 
Important files include:
build.sbt: contains the versions, resolvers and library dependancies.
application.conf: conatins configuration information including IP address, port number and communication information.
common_2.10-1.0.jar: is in the "lib" folder and contains all the libaries used.

* BitServer: Contains all the files and folders needed to run program on the server side.
Important files include:
build.sbt: contains the versions, resolvers and library dependancies.
application.conf: conatins configuration information including IP address, port number and communication information.
common_2.10-1.0.jar: is in the "lib" folder and contains all the libaries used.

* Common: Contains the common classes used on client side and server side and a copy of common needs to be made on client and server.
Contains case class definitions and the "class Useful" that contains the sha256 and generateString funtions.


REQUIREMENTS
------------
The following need to be installed to run the project:
* Scala
* Akka
* Sbt



INSTALLATION AND CONFIGURATION
------------------------------
 The folder BitClient and a copy of Common need to be copied at the client.
 The folder BitServer and a copy of Common need to be copied at the server.
 Go to the BitClient folder on client and BitServer folder on server using "cd" in command promt.
 Type "sbt run"
 When asked for enter the IP address of the location of BitServer and enter port number as "6000".
 This port number of the server can be changed by going in the "application.conf" file in the "resources" folder of "BitServer"
 The port number of the client can be changed by going in the "application.conf" file in the "resources" folder of "BitServer"
 The IP address of the client needs to be changed in the "application.conf" file in the "resources" folder of "BitClient" based on where the client is situated.
 The IP address of the server needs to be changed in the "application.conf" file in the "resources" folder of "BitServer" based on where the server is situated.
 Note: Make sure the server is running before you start the client



PROGRAM FLOW
------------
 On running the client(server should aldraedy have been started) you will be promted to enter the following input values:
 * The number of preceeding zeros you want to compute on the client
 * The number of preceeding zeros you want to compute on the server
 * The number of final string outputs(sucessfully mined coins) wanted on the client
 * The number of final string outputs(sucessfully mined coins) wanted on the server
 * IP address of the server,enter based on where server is situated (you can change location of server)
 * Port number of the server (default value is 6000) 
   Note: This can be changed by going in the "application.conf" file in the "resources" folder of "BitServer" and changing the "port" attribute from
   port=6000 to whatever value is desired.



PROJECT QUESTIONS ANSWERED
--------------------------
NOTE: As I did not have a project partner I used 2 terminals to test the program, one for client the other for server, on two seperate ports. 
      I also tested the project using friends computer as server to make sure it works.



Q1) Size of the work unit that you determined results in best performance for your implementation and an explanation on how you determined it. Size of the work unit refers to the number of sub-problems that a worker gets in a single request from the boss.

 * I did a lot of experiments with the value of the work unit. I used a round robin router to divide the work between workers. I did this after experimenting with Random Router, Snallest Mailbox first router and someothers. Round Robin gave the most consistent results. 

 * I ran the project with the following using different number of workers for the fixed work unit:
  preceeding zeros you want to compute on the client= 4
  preceeding zeros you want to compute on the server= 4
  number of final string outputs on the client= 4
  number of final string outputs on the server= 4
  IP address: 127.0.0.1
  Port number: 6000

 This consists of 4 work units on client and 4 on server. I changed the number of workers instantiated by router and noted down the reuslts.

 * Summary of reuslts(all times in ms)

 Router number of workers at client: 2
 Router number of workers at server: 4
 Average run time at client(based on 10 readings): 1153.3
 Average run time at server based on 10 readings): 5725.57

  Router number of workers at client: 6
 Router number of workers at server: 1
 Average run time at client(based on 10 readings): 6397.5
 Average run time at server based on 10 readings): 12385.25

  Router number of workers at client: 4
 Router number of workers at server: 8
Average run time at client(based on 10 readings): 6578.93
 Average run time at server based on 10 readings): 8673.98

  Router number of workers at client: 4
 Router number of workers at server: 12
Average run time at client(based on 10 readings): 7463.25
 Average run time at server based on 10 readings): 9849.64

 Router number of workers at client: 2
 Router number of workers at server: 2
Average run time at client(based on 10 readings): 6342.5
 Average run time at server based on 10 readings): 7275.45

I concluded that if the number of workers are equal to the number of cores maximum utiliation of resources occurs and less time in switching by pre-emmting. Also the if number of final strings needed(work units) is equal to the number of workers we get faster results. In my 2 core machine i tested with number of output strings=8 and workers=8 and got extremely fast results compared to less number of workers.

Hence, I decided to keep the number of workers equal to the number of desired output strings to get a balanced and consistently fast result.





Q2) The result of running your program for scala project1.scala 4

 * I ran the project with:
  preceeding zeros you want to compute on the client= 4
  preceeding zeros you want to compute on the server= 4
  number of final string outputs on the client= 5
  number of final string outputs on the server= 5
  IP address: 127.0.0.1
  Port number: 6000

  OUTPUT CLIENT:
  String number 1 is:  siddhant36568046_eLB1/hMU&HL}E^5$4~?`]^\h0
Corresponding Sha256 hash is: 0000e8120d980d35811c85b7738893b49d60c647f7ce5209ccd546bc27375652


String number 2 is:  siddhant36568046VnZY$dj[+?BUfbx?h>7X^U1Cc1%R0{gcA(J<+3F7yo/x(L_Y5zn?
Corresponding Sha256 hash is: 0000a666e0a195b10b8482ad7ee5cb4941667412cf21d07003a999c5e77a9868


String number 3 is:  siddhant36568046i\%4u\yL~LIKb>H?M;;{$$b;`SEbG)C?q
Corresponding Sha256 hash is: 0000593b52c6720d4ab037c726159ca08feb28ccd2885fc102ba732389fb2720


String number 4 is:  siddhant36568046E18)prY>~xKnyhe<Z+EuQC7Kir
Corresponding Sha256 hash is: 0000e14c5d5c26875254fe2802da29868e91cba7d0043ead3b106625b69f2888

[INFO] [09/13/2015 22:07:31.695] [BitcoinSystem-akka.actor.default-dispatcher-5] [NettyRemoteTransport(akka://BitcoinSystem@127.0.0.1:5150)] RemoteClientShutdown@akka://BitServerSystem@127.0.0.1:6000

String number 5 is:  siddhant36568046Ss`&%WNKuRSm_GV58l(z$%||\xw>^t0,JeHGROEV]O9M_PZUL3sZ*eZ
Corresponding Sha256 hash is: 0000d407d368ea95d02c8e0643c413e0f2d8573c720e03e213f2dcca1788f7ce


  OUTPUT SERVER:
  String number 1 is:  siddhant36568046<;-pq{!ZHU2P]U}4?t&6gmu~
Corresponding Sha256 hash is: 000039ccad356374bdb0b62f79668f0d8d2bf3471dcfd09e7132affd36713006


String number 2 is:  siddhant36568046fHTt=R:@0=pC;D;+[~u;[LV"jLaBa/2N-7T2V[BOvo7
Corresponding Sha256 hash is: 0000755f0478a6912e73e23b2111619e89a24106209df51b1cd212266ec74ca8


String number 3 is:  siddhant365680465Q"fC<H$xkZNM?pAqgi_jBTMvq;yrtPzt*+EDJ"F'%!*t8yhEqFe}y:KQWfUM`en;@q}JYCsot-o}H;`s.s83!;~<&4i[
Corresponding Sha256 hash is: 00007e505b69b72f921a94331153b758ec45c747aa3f80805e5e36ef47c07653


String number 4 is:  siddhant36568046cs):,=WOBwg(ufJ'kp_ScZVm9Sr{[wtTe%KeJeV`k1mg2;c;mxB?,(}\<vk^S<&x9_$VLgGI}erlN
Corresponding Sha256 hash is: 000060afae16ade652d161d050926f43f3aae2d3cd96465a2ec258b1950476d9


String number 5 is:  siddhant36568046}6a{;i@^$+u2"B}!-5$*06,7&?HT|VOdg!>(2SeR1,ZYGqBcfvL]Tr0~s2$'NwlP2UvzxL''[l,>BMz
Corresponding Sha256 hash is: 0000386c0de94d89699dbc963140a5b84d42b0c87335d5c0c772401e1821832c





Q3) The running time for the above as reported by time for the above, i.e. run time scala project1.scala 5 and report the time. The ratio of CPU time to REAL TIME tells you how many cores were effectively used in the computation. 
  
  NOTE: I ran both client and server on single dual core machine. Performance will drastically imporve if both server and client have intel i7 quad core 
        CPUs.

        Duration is printed at the end of the output in each casevas shown below

 OUTPUT CLIENT WITH DURTION:
 String number 1 is:  siddhant36568046TqfI).3;gF|t9pl"86]v
Corresponding Sha256 hash is: 00007ca979207fe2e6cb20c4ae65af9c458dad1fe2f3270864af48504c061f5e


String number 2 is:  siddhant36568046nadSi4^b(oZrFCjM+0U]($TjI$5$F,l?V_604O/=u<uPI1k`;{GP`&=hQ[_\Q`+*~f53\3c]WJ[-3W,OK(h
Corresponding Sha256 hash is: 0000024a5c1a29960022f7eea926e44b0d4f6f5f8b3defd893197903fd49fba8


String number 3 is:  siddhant36568046}*Nv~$w1n1Z]DyRGB+4my}'jo2`INO
Corresponding Sha256 hash is: 0000f100acca0572b06dbfe680228a9a31a88bde9d9f7416a9f6d51e5fe3de4e


String number 4 is:  siddhant36568046XwFo\3EE)}7c^a(ft;@HGZ*~)S5ENj69Xda68xIAgw>
Corresponding Sha256 hash is: 0000e9b27ce5be845f0c7d07d6f6c6a7ab4d3d39a335ab682844291fc3644e92


String number 5 is:  siddhant36568046'HFL*3{aink;0;qUQ56cC"T@h_SGT
Corresponding Sha256 hash is: 000016b64b412cfd16a20d55bc94be6561685c08b92538a279b686a14b109c01

duration is :6622 milliseconds

 
 OUTPUT SERVER WITH DURATION
 String number 1 is:  siddhant36568046YYf%Hmc6H}nim0KLJd"j1/M<ng7jO~>h1WnB*eZ-Y1}jdM]o(mw?ZxUfQ<'IlJ(fC
Corresponding Sha256 hash is: 0000d178018e64bc67d64792c38acccc0e945e44cb07ab6a30e94fc7a06007cf


String number 2 is:  siddhant365680464=&d_K:t6e+.C;]7]cO
Corresponding Sha256 hash is: 0000f9ff9cf158be62a278e309f645e9f7d18e02ee6ec3d7ebdb337420151d30

[INFO] [09/13/2015 22:24:24.797] [BitServerSystem-7] [NettyRemoteTransport(akka://BitServerSystem@127.0.0.1:6000)] RemoteClientShutdown@akka://BitcoinSystem@127.0.0.1:5150

String number 3 is:  siddhant36568046v7E
Corresponding Sha256 hash is: 000042cd12cc6d05d08003f629a72208248b3d549de1f03c0cab4f1022575b01


String number 4 is:  siddhant36568046t:[c%v:ws70"~3bHLH,
Corresponding Sha256 hash is: 0000baa2e144b5e7f175627623c982359f33a166446f830710591f6607d9b82d


String number 5 is:  siddhant36568046GQFmjoj7?o7;sdXSq8n$%4u{*banITSehV_2Qbr~<43vB[fUk|kFn>zwBFo2C"~n7cHk%x
Corresponding Sha256 hash is: 0000826c191d38c3924b434d4c50ab991e99c161f3c85c6ce0ee9516fcc4804f

duration is :9909 milliseconds





Q4) The coin with the most 0s you managed to find.
  NOTE: For more than 8 zeros more cores and powerful machines are needed to get the solution quicker. I am confident that I can connect my project to multiple quad core machines and achieve higher number of zeros in lesser time. 

  STRING: siddhant36568046vG/[W~q^=!$ntJxBEJPx3v$MC$Q8$Fe8UA+k4o*kC}~Ttl6;\0V*QcgTX2(GjVz45ai`N@$RqI8H9BGE?G>R=N8
  COIN  : 00000005046765806694e31ccf54823f6bc479e6a58c488fd39f99c9efd4ac31





Q5) The largest number of working machines you were able to run your code with.
 
 2 machines as I did not have the resources. However,  I am confident of modifying the code to make it run on multiple machines using single client and many servers.


DEVELOPER INFORMATION
---------------------
Siddhant Deshmukh
Master in Computer Science at University of Florida
Email: siddhantdeshmukh@ufl.edu    or   siddhantd28@gmail.com
UFID: 36568046
Phone: 415-630-0891


 




