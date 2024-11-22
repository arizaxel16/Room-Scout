# Stage 1: Build the application
FROM node:18-alpine AS builder

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application source code
COPY . .

# Build the React application
RUN npm run build

# Stage 2: Serve the static files using Nginx
FROM nginx:stable-alpine

# Copy built files from the builder stage to the Nginx HTML folder
COPY --from=builder /app/build /usr/share/nginx/html

# Expose the port the app runs on
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
