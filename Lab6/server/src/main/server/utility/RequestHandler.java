package server.utility;

import Lab5.common.interactions.Request;
import Lab5.common.interactions.Response;
import Lab5.common.interactions.ResponseCode;
import org.jetbrains.annotations.NotNull;


public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager){
        this.commandManager = commandManager;
    }
    public Response handle( Request request){
        commandManager.addToHistory(request.getCommandName());
        ResponseCode responseCode = executeCommand(request.getCommandName(),request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }
    private ResponseCode executeCommand(String command, String commandStringArgument, Object commandObjectArgument){
        switch(command){
            case "":
                break;
            case "help":
                if(!commandManager.help(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if(!commandManager.info(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if(!commandManager.show(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if(!commandManager.add(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if(!commandManager.update(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_any_by_semester_enum":
                if(!commandManager.removeAnyBySemesterEnum(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if(!commandManager.removeById(commandStringArgument,commandObjectArgument))
                    return  ResponseCode.ERROR;
                break;
            case "clear":
                if(!commandManager.clear(commandStringArgument,commandObjectArgument))
                    return  ResponseCode.ERROR;
                break;
            case "save":
                if(!commandManager.save(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if(!commandManager.executeScript(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add_if_min":
                if(!commandManager.addIfMin(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_greater":
                if(!commandManager.removeGreater(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "history":
                if(!commandManager.history(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "sum_of_transferred_students":
                if(!commandManager.sumOfTransferredStudents(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "filter_by_form_of_education":
                if(!commandManager.filterByFormOfEducation(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "server_exit":
                if(!commandManager.serverExit(commandStringArgument,commandObjectArgument))
                    return ResponseCode.ERROR;
                return ResponseCode.SERVER_EXIT;
            default:
                ResponseOutputer.appendln("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                return ResponseCode.ERROR;
        } return ResponseCode.OK;
    }


}
