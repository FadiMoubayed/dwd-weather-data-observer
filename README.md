The development of this version of the 52°North SOS was supported by
the German Federal Ministry of of Transport and Digital Infrastructure
research project WaCoDis (co-funded by the German Federal Ministry of
Transport and Digital Infrastructure, programme mFund)

# dwd-weather-data-observer
An application that fetches weather measurements from the DWD FTP server

## Progress so far
+ Connect to the DWD FTP server using FtpRemoteFileTemplate

+ Copy one file from the FTP server into my computer.

## Further work
- Inverstigate what the written methods return (InitializingBean)
- Maybe returning a File object or a list of Strings
- Inverstigate other methods to connecting to the FTP server (Filesynchronizer
  or Inbound channal)
