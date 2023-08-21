FROM node:16-alpine AS build
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build
EXPOSE 4200
CMD ["npm", "run", "start"]
