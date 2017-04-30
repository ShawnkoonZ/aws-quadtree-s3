<h1>aws-quadtree-java</h1>
<h2>If you decided to use any of the implementations, please 'Star' this Project!</h2>
<ul>
  <li><strong>Project Name</strong> : aws-quadtree-java</li>
  <li><strong>Project description</strong> : PR QuadTree implementation on AWS DynamoDB using Java.</li>
  
  <li><strong>Used Languages</strong> : Java</li>
  
  <li><strong>Used Editors</strong> : VSCode</li>
  
  <li><strong>Start Date</strong> : April, 2017</li>
</ul>

-----------
## Detailed description
pp: https://docs.google.com/presentation/d/1lqhEhYGnqDLwFNhti0ljM-ST1YVF0vknnQrLUN1x11w/edit?usp=sharing

  The program will compile and run via command line, then the program will generate a QuadTree based on the input (xMin,yMin,xMax,yMax). Once the Tree is generated, the program will output the Tree into a file and simultaneously implement the QuadTree on AWS DynamoDB. Our future idea is to be able to feed the output file(s) up to an S3 bucket and use the Lambda function to deploy EC2 instances on an ECS cluster. When an instance receives the QuadTree information, it will create the QuadTree in an asynchronous manner.
  
-----------
## Project Requirements.

- Figure out how many output files will be generated based on x / y axis arguments.
- Insert each output files into Specific AWS S3 bucket as they are created. 

-----------
## Instruction ( Developing ).

1. Fork & Clone this repository.

2. Set up AWS Credentials. (**See the Quick Start Below**)

3. Install AWS Java SDK on your machine.

4. Compile java files using `javac *.java`

5. Run the compiled `QuadTreeTester.java` file by typing `java QuadTreeTester <xMin> <yMin> <xMax> <yMax>`

6. Enjoy.

-----------
Quick Start
-----------

Set up credentials (in e.g. ``~/.aws/credentials``):

    [default]
    aws_access_key_id = YOUR_KEY
    aws_secret_access_key = YOUR_SECRET

Then, set up a default region (in e.g. ``~/.aws/config``):

    [default]
    region=us-east-1

