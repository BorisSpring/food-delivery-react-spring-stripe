import React from 'react';
import GoogleLogin from 'react-google-login';
import { useEffect } from 'react';
import { gapi } from 'gapi-script';

const GoogleSocialLogin = () => {
  const onSuccess = (res) => {
    console.log('Sucess login: ', res);
  };

  const onFailure = (res) => {
    console.log('Fail to login:', res);
  };

  useEffect(() => {
    function start() {
      gapi.client.init({
        clientId:
          '803735934987-ecejvvm3pa97t2d6tbo4hlq7ttfau1e7.apps.googleusercontent.com',
        scope: '',
      });
    }
    gapi.load('client:auth2', start);
  }, []);

  return (
    <>
      <GoogleLogin
        clientId='803735934987-ecejvvm3pa97t2d6tbo4hlq7ttfau1e7.apps.googleusercontent.com'
        buttonText='Login With Google'
        isSignedIn='true'
        onSuccess={onSuccess}
        onFailure={onFailure}
        cookiePolicy={'single_host_origin'}
      />
    </>
  );
};

export default GoogleSocialLogin;
