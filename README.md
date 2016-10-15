# SimpleQuartzJob
Simple quartz Example

if your app runs in a cluster, you have no choice. Spring scheduler keeps everything in memory and will not synchronize jobs between several instances of your app. Every instance will run all the jobs thinking it is alone. If it's not what you want, you'll have to use Quartz with a database. 
