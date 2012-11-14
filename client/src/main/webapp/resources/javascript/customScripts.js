// GLOBAL DEFINITIONS

var HEADER_SHOW_SECONDS = true; // shows seconds on header
var HEADER_UPDATE_RATE = 1000; // refresh rate 30000 miliseconds ~> 30s




function display_c()
{	
	mytime=setTimeout('display_ct()',HEADER_UPDATE_RATE)
}

function display_ct() 
{
	var strcount
	var x = new Date()
	var output = x.getHours() + ':';
        if(x.getMinutes()<10)
        {
            output += '0'+x.getMinutes();
        }
        else
        {
            output += x.getMinutes();
        }
	if(HEADER_SHOW_SECONDS)
	{
		if(x.getSeconds()< 10)
		{
			output += ':0'+x.getSeconds();
		}
		else
		{
			output += ':'+x.getSeconds();
		}
	}
	document.getElementById('ct').innerHTML = output;
	tt=display_c();
}