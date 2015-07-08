var canvas='';
var stop=false;
var stream_=null;
function qr_scan(){
    try{
        var ctx = canvas.getContext("2d");
        ctx.drawImage(video, 0, 0, 640, 480);
        qrcode.decode();

    }catch(e){
    }
    if(stop)
        return;
    setTimeout(qr_scan, 50);
};


function qr_stop(video_id){
    stop=true;
    video = document.getElementById(video_id);
    video.pause();
    video.src='';
    stream_.stop();
    var ctx=canvas.getContext("2d");
    ctx.clearRect ( 0 , 0 , canvas.width, canvas.height );
}
function qr_startagain(video_id){
    stop=false;
    video = document.getElementById(video_id);
    video.play();
}
function qr_init(video_id,callback) {
    $('#qrcode_container').show();

    stop=false;
    qrcode.callback = function (res) {
        callback(res);

    };
    // Grab elements, create settings, etc.

    // content = canvas.getContext("2d"),
    videoObj = { "video": true },
        errBack = function (error) {
            console.log("Video capture error: ", error.code);
        };

    // Put video listeners into place
    if (navigator.webkitGetUserMedia) { // WebKit-prefixed
        navigator.webkitGetUserMedia(videoObj, function (stream) {
            stream_=stream;
            canvas = document.getElementById("qr-canvas"),

                video = document.getElementById(video_id);

            video.src = window.webkitURL.createObjectURL(stream);
            video.play();
            qr_scan();

        }, errBack);
    }
    else if (navigator.mozGetUserMedia) { // Firefox-prefixed
        navigator.mozGetUserMedia(videoObj, function (stream) {
            stream_=stream;
            canvas = document.getElementById("qr-canvas");

            video = document.getElementById(video_id);

            video.src = window.URL.createObjectURL(stream);
            video.play();
            qr_scan();

        }, errBack);
    }
    else if (navigator.getUserMedia) { // Standard
        navigator.getUserMedia(videoObj, function (stream) {
            stream_=stream;
            canvas = document.getElementById("qr-canvas");

            video = document.getElementById(video_id);

            video.src = stream;
            video.play();
            qr_scan();

        }, errBack);
    }

}
