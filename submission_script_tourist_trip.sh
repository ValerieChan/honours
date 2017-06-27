##$ -S /bin/sh
##$ -wd /vol/grid-solar/sgeusers/chanvale1
##$ -M chanvale1@ecs.vuw.ac.nz 
##$ -m be 

ALGO="simplegp"
SCENARIO=$1
GRID_PATH="/vol/grid-solar/sgeusers/chanvale1/honours/"$ALGO
JAR_PATH="/vol/grid-solar/sgeusers/chanvale1/honours/package"

if [ ! -d $GRID_PATH/$SCENARIO ]; then
  mkdir $GRID_PATH/$SCENARIO
fi

mkdir -p /local/tmp/chanvale1/$JOB_ID 

if [ -d /local/tmp/chanvale1/$JOB_ID ]; then
        cd /local/tmp/chanvale1/$JOB_ID
else
        echo "There's no job directory to change into "
        echo "Here's LOCAL TMP "
        ls -la /local/tmp
        echo "AND LOCAL TMP CHANVALE1 "
        ls -la /local/tmp/chanvale1
        echo "Exiting"
        exit 1
fi

cp $JAR_PATH/honoursTourist.jar .
cp -r $GRID_PATH/params ./params
##cp -r $GRID_PATH/params ./txt
sleep 2

##/usr/pkg/java/sun-8/bin/java -jar gprun.jar -file params/$ALGO.params -p seed.0=$(($SGE_TASK_ID-1)) -p stat.file=job.$(($SGE_TASK_ID-1)).out.stat 
/usr/pkg/java/sun-8/bin/java -jar honoursTourist.jar -file params/$ALGO.params -p seed.0=$(($SGE_TASK_ID-1)) -p stat.file=a.job.$(($SGE_TASK_ID-1)).out.stat -p eval.problem.eval-model.sim-models.catergory = params/TDOP/dataset$1/arc_cat_$1.txt -p eval.problem.eval-model.sim-models.filename = params/TDOP/dataset$1/OP_instances/p$1.1.a.txt

/usr/pkg/java/sun-8/bin/java -jar honoursTourist.jar -file params/$ALGO.params -p seed.0=$(($SGE_TASK_ID-1)) -p stat.file=b.job.$(($SGE_TASK_ID-1)).out.stat -p eval.problem.eval-model.sim-models.catergory = params/TDOP/dataset$1/arc_cat_$1.txt -p eval.problem.eval-model.sim-models.filename = params/TDOP/dataset$1/OP_instances/p$1.1.b.txt

cp params/*.stat $GRID_PATH/$SCENARIO
##cp params/*.csv $GRID_PATH/$SCENARIO
cp *.stat $GRID_PATH/$SCENARIO
##cp * $GRID_PATH ##/ #$SCENARIO
cp *.csv $GRID_PATH/$SCENARIO 
cd $GRID_PATH/$SCENARIO
pwd
rm -fr /local/tmp/chanvale1/$JOB_ID
