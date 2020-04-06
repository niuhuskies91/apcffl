# Image
FROM mhart/alpine-node:10

# Vars
ARG ENV=${ENV:-dev}

# Copy / Move Files
WORKDIR /apcffl-ui
COPY . /apcffl-ui
RUN cd /apcffl-ui

# Dependencies / Build
RUN npm install
RUN npm run build

# Start Up
EXPOSE 8080
CMD npm run start:${ENV}
