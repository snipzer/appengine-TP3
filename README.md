# Lancement du WebServer
   
    docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java
    
# Deploiement
    
    docker run --rm -it -h localhost -v ~/.m2:/root/.m2 -v $(pwd):/usr/src/app -v ~/.config/gcloud:/root/.config/gcloud -w /usr/src/app -p 8080:8080 zenika/alpine-appengine-java bash
    
# Puis 

    gcloud auth login
    #copy paste the url in your browser and then paste the token in your bash
    gcloud config set project epsi-20181212-loicarif
    gcloud config set app/promote_by_default false
    mvn -Dapp.deploy.version=tp2-v2 appengine:deploy