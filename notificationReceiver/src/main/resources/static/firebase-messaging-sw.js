importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');

// Initialize Firebase
const config = {
    apiKey: "AIzaSyD1wy9WWcYK0bxg6OfUYxSKMnwo827JykI",
    authDomain: "notificationreceiver-2bcff.firebaseapp.com",
    projectId: "notificationreceiver-2bcff",
    storageBucket: "notificationreceiver-2bcff.appspot.com",
    messagingSenderId: "12801059240",
    appId: "1:12801059240:web:42de3553a36a1549c1d59d",
    measurementId: "G-NDH45YETLJ"
};

firebase.initializeApp(config);

const messaging = firebase.messaging();
messaging.setBackgroundMessageHandler(function(payload){

    const title = "Hello World";
    const options = {
        body: payload.data.status
    };

    return self.registration.showNotification(title,options);
});
