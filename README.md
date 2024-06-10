# ScaffoldIncorrectPadding
Minimal project to reproduce Scaffold bug with incorrect paddings calculation

When `ScaffoldSubcomposeInMeasureFix` is true, the padding values parameter sometimes appears in the 
content block of our Scaffold without the status bar padding
(sometimes the same thing happens with the bottom navigation paddings, but I'm not sure if it's related to that issue).

Due to this, the indentation for the first element is not calculated correctly and it climbs onto the top bar.

![incorrect_padding.png](./incorrect_padding.png)
The screenshot shows a scaffold with a top bar (marked as 1) and a lazy column in the content.
It can be seen that the top padding of the zero-element of lazy column (marked as 2) does not 
take the height of the status bar. This can also be seen from the text inside the top bar and the 
zero-element lazy column.

If the `ScaffoldSubcomposeInMeasureFix` flag is set to false, the height becomes correct:

![correct_padding.png](./correct_padding.png)

