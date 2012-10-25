uphea
=====

**Uphea** is an open-source real-world web application example built on **Jodd** frameworks. It presents many best-practices of using **Jodd**. Moreover, **uphea** gives an excellent overview of key points and main strengths of Jodd framework; you will be able to feel the *unbearable lightness* of Java web development. We decided to give you a chance to examine the real web application, and not only some half-baked pet-store example.

For more details and key-points, see: http://jodd.org/uphea

See screenshots here: http://uphea.com


## Running Uphea

To run **uphea**, you must set environment variable:

	uphea.dir

that points to a *working* folder with images, logs and database (folder `work` in the repo).


## Uphea bundle

You can download prepared **Uphea** bundle from **Jodd** website.
Then just unzip and run it to see it in action!

### Building Uphea bundle

You can build your own bundle using **Ant** and following commands:

	ant clean
	ant dist
    ant pack

This will create **Uphea** distribution `dist/uphea.zip`.