def call(String imageName, String credentialsId) {
    echo "Logging in to Harbor"

    withCredentials([
        usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'HARBOR_USERNAME',
            passwordVariable: 'HARBOR_PASSWORD'
        )
    ]) {

        sh """
            echo "\$HARBOR_PASSWORD" | docker login \
                -u "\$HARBOR_USERNAME" \
                --password-stdin \
                ${getRegistry(imageName)}

            docker push ${imageName}
        """
    }

    echo "Docker image pushed successfully: ${imageName}"
}


def getRegistry(String imageName) {
    return imageName.split('/')[0]
}
