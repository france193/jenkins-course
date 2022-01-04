// name that will have jenkins job
job('NodeJS Docker example - 2') {
    scm {
        // github repo where to take nodejs app to create docker container
        git('https://github.com/france193/docker-demo.git') {  node ->
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        // this is the name of the NodeJS installation in 
        // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
        nodejs('nodejs')                        
    }
    steps {
        dockerBuildAndPublish {
            // will publish a new docker image at this repo name
            repositoryName('france193/docker-nodejs-demo-2')
            tag('${GIT_REVISION,length=9}')
            // take dockerhub credentials with this name (dockerhub)
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
