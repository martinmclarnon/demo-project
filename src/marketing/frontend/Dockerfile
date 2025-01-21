
FROM node:lts AS build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
RUN npm install axios --save
COPY .env.local ./
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
