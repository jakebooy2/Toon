package me.jakebooy.jframework.commands;

import me.jakebooy.jframework.JFramework;
import me.jakebooy.jframework.handler.AbstractCommand;
import me.jakebooy.jframework.permissions.ToonUser;
import me.jakebooy.jframework.roles.JRole;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jakebooy on 14/05/17.
 */
public class Perm extends AbstractCommand {

    private JFramework jFramework;
    public Perm(JFramework jFramework){
        super("perm");
        this.jFramework = jFramework;
    }

    @Override
    public void execute(Guild server, Message message, ToonUser user, String[] args) {
        if(user.hasPermission("toon.admin.perm")){
            if(args.length == 0){
                String prefix = " - ";
                String permHelp = "**>** Permission Help\n" +
                        prefix + "perm user **@user** add **permission** \n" +
                        prefix + "perm user **@user** remove **permission** \n" +
                        prefix + "perm role **role** add **permission** \n" +
                        prefix + "perm role **role** remove **permission** \n" +
                        prefix + "perm user **@user** permissions \n" +
                        prefix + "perm user **@user** roles \n" +
                        prefix + "perm role **role** permissions ";
                message.getTextChannel().sendMessage(permHelp).queue();
                return;
            }
            if(args[0].equalsIgnoreCase("user")){
                if(args.length > 2){
                    User t = message.getMentionedUsers().get(0);
                    String a = "";
                    for(int i = 1; i < args.length; i++){
                        a+= args[i] + " ";
                    }
                    a = a.replaceAll("@"+ server.getMember(message.getMentionedUsers().get(0)).getEffectiveName() + " ", "");
                    String[] arg = a.split(" ");

                    if(arg[0].equalsIgnoreCase("add")){
                        if(arg.length == 2){
                            String permission = arg[1];
                            ToonUser target = jFramework.getPermissionManager().getPermissionUser(t.getId());
                            if(target.addPermission(permission)){
                                message.getTextChannel().sendMessage("Permission **" + permission + "** added to **" + target.getMember().getEffectiveName() + "**!").queue();
                            }else{
                                message.getTextChannel().sendMessage("User already has permission!").queue();
                            }
                        }else{
                            message.getTextChannel().sendMessage("Usage: perm user **@user** add **permission**").queue();
                        }
                        return;
                    }

                    if(arg[0].equalsIgnoreCase("remove")){
                        if(arg.length == 2){
                            String permission = arg[1];
                            ToonUser target = jFramework.getPermissionManager().getPermissionUser(t.getId());
                            if(target.removePermission(permission)){
                                message.getTextChannel().sendMessage("Permission **" + permission + "** removed from **" + target.getMember().getEffectiveName() + "**!").queue();
                            }else{
                                message.getTextChannel().sendMessage("User doesn't have permission!").queue();
                            }
                        }else{
                            message.getTextChannel().sendMessage("Usage: perm user **@user** remove **permission**").queue();
                        }
                        return;
                    }

                    if(arg[0].equalsIgnoreCase("permissions")){
                        ToonUser target = jFramework.getPermissionManager().getPermissionUser(t.getId());
                        if(target.getPermissions().isEmpty()){
                            message.getTextChannel().sendMessage("**" + target.getMember().getEffectiveName() + "** has no permissions.").queue();
                            return;
                        }
                        StringJoiner join = new StringJoiner(", ");
                        for(String permission : target.getPermissions()){
                            join.add("`" + permission + "`");
                        }
                        message.getTextChannel().sendMessage("**" + target.getMember().getEffectiveName() + "'s permissions:**\n" + join.toString()).queue();
                        return;
                    }

                    if(arg[0].equalsIgnoreCase("roles")){
                        ToonUser target = jFramework.getPermissionManager().getPermissionUser(t.getId());
                        StringJoiner join = new StringJoiner(", ");
                        for(String role : target.getRoles()){
                            join.add("`" + role + "`");
                        }
                        if(target.getRoles().isEmpty()){
                            message.getTextChannel().sendMessage("**" + target.getMember().getEffectiveName() + "** has no roles.").queue();
                            return;
                        }
                        message.getTextChannel().sendMessage("**" + target.getMember().getEffectiveName() + "'s roles:**\n" + join.toString()).queue();
                        return;
                    }
                }
                String prefix = " - ";
                String permHelp = "**>** Permission Help\n" +
                        prefix + "perm user **@user** add **permission** \n" +
                        prefix + "perm user **@user** remove **permission** \n" +
                        prefix + "perm user **@user** permissions \n" +
                        prefix + "perm user **@user** roles \n";
                message.getTextChannel().sendMessage(permHelp).queue();
                return;
            }
            if(args[0].equalsIgnoreCase("role")){

                String role = "";


                String a = "";
                for(int i = 1; i < args.length; i++){
                    a+= args[i] + " ";
                }

                Pattern p = Pattern.compile("\\\"(.*?)\\\"");
                Matcher m = p.matcher(a);
                while(m.find()) {
                    role = m.group(1);
                }

                System.out.println(a);
                a = a.replace("\"" + role + "\" ", "");
                System.out.println(a);
                String[] arg = a.split(" ");

                if(arg[0].equalsIgnoreCase("add")){
                    JRole r = jFramework.getRoleManager().getRole(role);
                    String permission = arg[1];
                    if(r.addPermission(permission)){
                        message.getTextChannel().sendMessage("Permission **" + permission + "** added to role **" + r.getName() + "**!").queue();
                    }else message.getTextChannel().sendMessage("Role already has permission!").queue();
                    return;
                }

                if(arg[0].equalsIgnoreCase("remove")){
                    JRole r = jFramework.getRoleManager().getRole(role);
                    String permission = arg[1];
                    if(r.removePermission(permission)){
                        message.getTextChannel().sendMessage("Permission **" + permission + "** removed from role **" + r.getName() + "**!").queue();
                    }else message.getTextChannel().sendMessage("Role doesn't have permission!").queue();
                    return;
                }

                if(arg[0].equalsIgnoreCase("permissions")){
                    JRole r = jFramework.getRoleManager().getRole(role);
                    if(r.getPermissions().isEmpty()){
                        message.getTextChannel().sendMessage("**Role " + r.getName() + "** has no permissions.").queue();
                        return;
                    }
                    StringJoiner join = new StringJoiner(", ");
                    for(String permission : r.getPermissions()){
                        join.add("`" + permission + "`");
                    }
                    message.getTextChannel().sendMessage("**Role " + r.getName() + "'s permissions:**\n" + join.toString()).queue();
                    return;
                }
                String prefix = " - ";
                String permHelp = "**>** Permission Help\n" +
                        prefix + "perm role **role** add **permission** \n" +
                        prefix + "perm role **role** remove **permission** \n" +
                        prefix + "perm role **role** permissions ";
                message.getTextChannel().sendMessage(permHelp).queue();
                return;
            }
        }else{
            message.getTextChannel().sendMessage("It looks like you're not authorized to do that!").queue();
            return;
        }
    }
}
