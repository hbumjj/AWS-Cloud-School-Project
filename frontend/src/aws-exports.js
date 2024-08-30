// src/aws-exports.js
const awsConfig = {
    Auth: {
      Cognito: {
        region: 'ap-northeast-2', // AWS Region
        userPoolId: 'ap-northeast-2_ChmgZntvX', // User Pool ID
        userPoolClientId: 'td4qcb7km0lee25j8uqmlqemi', // App client ID
        authenticationFlowType: 'USER_PASSWORD_AUTH',
        mandatorySignIn: false,
        // OPTIONAL - Hosted UI configuration
        oauth: {
          domain: 'https://so-cute-test.auth.ap-northeast-2.amazoncognito.com',
          scope: ['email', 'openid'],
          redirectSignIn: 'http://localhost:3000/',
          redirectSignOut: 'http://localhost:3000/',
          responseType: 'code',
        },
      }
    },
  };
  
  export default awsConfig;