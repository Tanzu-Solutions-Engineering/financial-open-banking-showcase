   #!/usr/bin/env bash
   
   #INSTALLING TBS
   
   (you may try with the kpack opensource project https://github.com/pivotal/kpack/blob/main/docs/tutorial.md)
   
   #https://docs.pivotal.io/build-service/1-1/installing.html
   
   #example:
     ytt -f $TBS_INSTALL_DIR/values.yaml \
          -f $TBS_INSTALL_DIR/manifests/ \
          -v docker_repository="$IMG_REGISTRY_URL/$IMG_REGISTRY_SYSTEM_REPO/build-service" \
          -v docker_username=$IMG_REGISTRY_USER \
          -v docker_password="$IMG_REGISTRY_PASSWORD" \
          | kbld -f $TBS_INSTALL_DIR/images-relocated.lock -f- \
          | kapp deploy -a tanzu-build-service -f- -y

  # ADDING BUILDPACKS
  #https://docs.pivotal.io/build-service/1-0/updating-deps.html
    
   # example:
     kp import -f descriptor-full.yaml
      
  
  #creating open-banking images:

  kp image save open-banking-atm-service -n dekt-apps \
        --tag harbor.apps.cf.tanzutime.com/dekt-apps/open-banking-atm-service:0.0.1 \
        --git https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase \
        --sub-path ./applications/atm-rest-service \
        --git-revision main \
        --wait

   kp image save open-banking-bank-service -n dekt-apps \
        --tag harbor.apps.cf.tanzutime.com/dekt-apps/open-banking-bank-service:0.0.1 \
        --git https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase \
        --sub-path ./applications/bank-rest-service \
        --git-revision main \
        --wait

   kp image save open-banking-database-service -n dekt-apps \
        --tag harbor.apps.cf.tanzutime.com/dekt-apps/open-banking-database-service:0.0.1 \
        --git https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase \
        --sub-path ./applications/database-sor-migration \
        --git-revision main \
        --wait

   kp image save open-banking-account-service -n dekt-apps \
        --tag harbor.apps.cf.tanzutime.com/dekt-apps/open-banking-account-service:0.0.1 \
        --git https://github.com/Tanzu-Solutions-Engineering/financial-open-banking-showcase \
        --sub-path ./applications/acount-rest-service \
        --git-revision main \
        --wait
