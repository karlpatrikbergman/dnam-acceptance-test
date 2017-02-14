FROM centos:7

RUN yum update -y && yum clean all
WORKDIR /app
ADD node.sh /app/node.sh
ADD nodes.txt /app/nodes.txt
ADD nodes_setup.sh /app/nodes_setup.sh

ENTRYPOINT ["/bin/bash", "./nodes_setup.sh"]