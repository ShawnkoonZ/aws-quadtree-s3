[![Build Status](http://34.208.250.40:8080/job/aws-quadtree-java/badge/icon)](http://34.208.250.40:8080/job/aws-quadtree-java/)
<h1>aws-quadtree-java</h1>
<h2>If you decided to use any of the implementations, please 'Star' this Project!</h2>
<ul>
  <li><strong>Project Name</strong> : aws-quadtree-java</li>
  <li><strong>Project description</strong> : QuadTree implementation on AWS DynamoDB using Java.</li>
  
  <li><strong>Used Languages</strong> : Java</li>
  
  <li><strong>Used Editors</strong> : VSCode</li>
  
  <li><strong>Start Date</strong> : April, 2017</li>
</ul>

-----------
## Detailed description
pp: https://docs.google.com/presentation/d/1lqhEhYGnqDLwFNhti0ljM-ST1YVF0vknnQrLUN1x11w/edit?usp=sharing

  Program will compile & run via command line, then the program will generate QuadTree based on the input x-axis & y-axis. Once the Tree is generated, program will output the tree into a file & at the same time implement QuadTree on AWS DynamoDB. Future idea is to be able to feed output file upto S3 bucket, Lambda function deploys EC2 instances on ECS cluster. Once each instances retreive QuadTree information, they all create QuadTree in Async manner.
  
-----------
## Project Requirements.

- Figure out how many output files will be generated based on x / y axis arguments.
- Insert each output files into Specific AWS S3 bucket as they are created. 

-----------
## Instruction ( Developing ).

1. Fork & Clone this repository.

2. Set up AWS Credentials. (**See the Quick Start Below**)

3. Install AWS Java SDK on your machine.

4. Compile the `QuadTreeTester.java` file using `javac ...`.

5. Run compiled `QuadTreeTester.java` file by typing `java QuadTreeTester`.

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


