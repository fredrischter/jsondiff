# jsondiff
Provide JSON comparison feature

## Implementation choices

I have choosen to use com.flipkart.zjsonpatch to compare the JSON contents.

## Testing

Tested using integration tests as well as unit test.

## Banner

The banner is not good but it is the best I could do.

## Next steps

### More endpoints

An endpoint that receives two JSONs once and gives the difference output once.

### Tests

Make more tests with lots of different corner cases, for example really big JSONs, checking if the format is fine or not.

### Exception handler

Make an global exception handler to handle each kind of different modelled exception and respond with proper status and message.

### Banner

Find a better idea for the banner and change it.

### Web interface

The next steps could be an HTML generator endpoint that output highlighted text for differences.

Making an static web page that allows pasting JSON and getting that highlighted difference automaticly requesting it from the server.

Killing this java API endpoint and using a JS tool and let that comparison entirely on the frontend!
