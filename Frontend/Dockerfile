# Use official Node.js image
FROM node:14

# Set working directory in container
WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy the source code to the container
COPY . .

# Build the app
RUN npm run build

# Serve the app using a lightweight node server
FROM node:14-alpine
WORKDIR /app
COPY --from=0 /app/build /app/build
RUN npm install serve
CMD ["npx", "serve", "-s", "build", "-l", "3000"]