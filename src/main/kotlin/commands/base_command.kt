package commands

// Command
interface StakingCommand {
    fun execute()
}

// Handles commands whether it should add commands to a queue or process commands in queue
class CommandProcessor {
    private val queue = ArrayList<StakingCommand>()

    fun addToQueue(stakingCommand: StakingCommand): CommandProcessor =
        apply {
            queue.add(stakingCommand)
        }

    fun processCommand(): CommandProcessor =
        apply {
            queue.first().execute()
            queue.removeFirst()
        }
}