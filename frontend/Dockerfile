FROM nginx:latest
COPY  nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY ../build /usr/share/nginx/html
COPY ../build /app
CMD ["nginx", "-g", "daemon off;"]