module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/vue3-essential',
    '@vue/airbnb',
    '@vue/typescript/recommended',
  ],
  parserOptions: {
    ecmaVersion: 2020,
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'max-len': [2, 200, 2],
    'object-curly-newline': [
      'warn', {
        ObjectExpression: { multiline: true, minProperties: 5, consistent: true },
        ObjectPattern: { multiline: true, minProperties: 5, consistent: true },
        ImportDeclaration: 'never',
        ExportDeclaration: 'never',
      },
    ],
  },
};
