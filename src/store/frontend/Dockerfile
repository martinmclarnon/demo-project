
FROM node:lts AS build-stage
WORKDIR /app
COPY package*.json ./
RUN npm cache clean --force
RUN rm -rf node_modules package-lock.json
RUN npm install
RUN npm install axios --save
RUN npm install vue-router@next
RUN npx @sentry/wizard@latest -i nextjs
RUN npm install @opentelemetry/api
RUN npm install @opentelemetry/sdk-trace-web
RUN npm install @opentelemetry/exporter-trace-otlp-http
COPY .env.local ./
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
