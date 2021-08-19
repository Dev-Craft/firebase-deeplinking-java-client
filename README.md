# Java Client for Firebase Dynamic Links

### Releasing a new build of this client
1. Increment the version of the client by increasing major or minor number depending on the changes made
2. Build the project with: ```./gradlew build```
3. Copy the generated jar to the cashbox-dss-server/libs folder
4. Update build.gradle file of the cashbox-dss-server project to point to the new version number of this library
5. Remove the references to the old library
