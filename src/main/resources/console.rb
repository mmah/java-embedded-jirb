require 'stringio'
require 'irb'
require 'irb/completion'

#p 'what the heck is going on?'

class ConsoleInputMethod
  # echo the prompt and get a line of input.
  def gets
 #  $stdout.print 'jirb:> '    
    $stdin.gets
  end

  def prompt=(x)
  end
end
#----------------------------

class ConsoleIrb < IRB::Irb

  def initialize(ec_inputmethod)
    IRB.setup(__FILE__)
    IRB.conf[:VERBOSE] = false
    super(nil, ec_inputmethod)
  end

  def run
    IRB.conf[:MAIN_CONTEXT] = self.context
    eval_input
  end
end

#----------------------------

#IRB.start(__FILE__)

eiMethod = ConsoleInputMethod.new
eIrb = ConsoleIrb.new(eiMethod)

eIrb.run
