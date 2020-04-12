# apcffl-ui

## Running In Development Mode

This project requires Node.js 10+.

Install dependencies:

    npm install

Run development server:

    npm run watch

## Testing

Test files are located adjacent to the files which they test.

### Run test suite

    npm run test -- --watch

## Linting

    npm run lint

## Project Structure

### `config`

Contains environment and tooling configs.

### `public`

Contains the built project and static files.

### `scripts`

Contains shell scripts for tasks too large to fit directly in `package.json`.

### `server`

Contains the Koa Node.js server application.

This exists to serve the React application and to proxy requests to the API.

### `src`

Contains the source files for the React application.
